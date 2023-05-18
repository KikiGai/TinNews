package com.Ako.tinnews.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Ako.tinnews.R;
import com.Ako.tinnews.databinding.FragmentHomeBinding;
import com.Ako.tinnews.model.Article;
import com.Ako.tinnews.repository.NewsRepository;
import com.Ako.tinnews.repository.NewsViewModelFactory;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements CardStackListener {

    private HomeViewModel viewModel;

    private FragmentHomeBinding binding;

    private CardStackLayoutManager cardStackLayoutManager;

    private List<Article> articles;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        // setup card stack view
        CardSwipeAdapter cardSwipeAdapter = new CardSwipeAdapter();
        // use the CardStackLayout
        cardStackLayoutManager = new CardStackLayoutManager(requireContext(), this);
        cardStackLayoutManager.setStackFrom(StackFrom.Top);
        binding.homeCardStackView.setLayoutManager(cardStackLayoutManager);
        binding.homeCardStackView.setAdapter(cardSwipeAdapter);

        binding.homeLikeButton.setOnClickListener(v -> swipeCard(Direction.Right));
        binding.homeDislikeButton.setOnClickListener(v -> swipeCard(Direction.Left));
        binding.homeRewindButton.setOnClickListener(v -> rewindCard());

        NewsRepository repository = new NewsRepository();
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(HomeViewModel.class);
        viewModel.setCountryInput("us");
        viewModel
                .getTopHeadlines()
                .observe(
                        getViewLifecycleOwner(),
                        newsResponse -> {
                            if(newsResponse != null){
                                articles = newsResponse.articles;
                                cardSwipeAdapter.setArticles(articles);
                                Log.d("HomeFragment", newsResponse.toString());
                            }
                        }
                );
    }


    private void swipeCard(Direction direction){
        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                .setDirection(direction)
                .setDuration(Duration.Normal.duration)
                .build();
        cardStackLayoutManager.setSwipeAnimationSetting(setting);
        binding.homeCardStackView.swipe();
    }

    private void rewindCard(){
        RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .build();
        cardStackLayoutManager.setRewindAnimationSetting(setting);
        binding.homeCardStackView.rewind();
    }

    @Override
    public void onCardDragging(Direction direction, float v) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if(direction == Direction.Right){
            Log.d("CardStackView", "onCardSwiped: like " + cardStackLayoutManager.getTopPosition());
            Article article = articles.get(cardStackLayoutManager.getTopPosition() - 1);
            viewModel.setFavoriteArticleInput(article);
        }else if(direction == Direction.Left){
            Log.d("CardStackView", "onCardSwiped: dislike " + cardStackLayoutManager.getTopPosition());
        }
    }

    @Override
    public void onCardRewound() {
        Log.d("CardStackView", "onCardRewound: " + cardStackLayoutManager.getTopPosition());
    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int i) {

    }

    @Override
    public void onCardDisappeared(View view, int i) {

    }
}
// Generated by view binder compiler. Do not edit!
package com.example.umcflo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.umcflo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final BottomNavigationView mainBnv;

  @NonNull
  public final FrameLayout mainFrm;

  @NonNull
  public final ImageView mainMiniplayerBtn;

  @NonNull
  public final ImageView mainPauseBtn;

  @NonNull
  public final ConstraintLayout mainPlayerCl;

  @NonNull
  public final TextView miniPlayerSingerTv;

  @NonNull
  public final TextView miniPlayerTitleTv;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView,
      @NonNull BottomNavigationView mainBnv, @NonNull FrameLayout mainFrm,
      @NonNull ImageView mainMiniplayerBtn, @NonNull ImageView mainPauseBtn,
      @NonNull ConstraintLayout mainPlayerCl, @NonNull TextView miniPlayerSingerTv,
      @NonNull TextView miniPlayerTitleTv) {
    this.rootView = rootView;
    this.mainBnv = mainBnv;
    this.mainFrm = mainFrm;
    this.mainMiniplayerBtn = mainMiniplayerBtn;
    this.mainPauseBtn = mainPauseBtn;
    this.mainPlayerCl = mainPlayerCl;
    this.miniPlayerSingerTv = miniPlayerSingerTv;
    this.miniPlayerTitleTv = miniPlayerTitleTv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.main_bnv;
      BottomNavigationView mainBnv = ViewBindings.findChildViewById(rootView, id);
      if (mainBnv == null) {
        break missingId;
      }

      id = R.id.main_frm;
      FrameLayout mainFrm = ViewBindings.findChildViewById(rootView, id);
      if (mainFrm == null) {
        break missingId;
      }

      id = R.id.main_miniplayer_btn;
      ImageView mainMiniplayerBtn = ViewBindings.findChildViewById(rootView, id);
      if (mainMiniplayerBtn == null) {
        break missingId;
      }

      id = R.id.main_pause_btn;
      ImageView mainPauseBtn = ViewBindings.findChildViewById(rootView, id);
      if (mainPauseBtn == null) {
        break missingId;
      }

      id = R.id.main_player_cl;
      ConstraintLayout mainPlayerCl = ViewBindings.findChildViewById(rootView, id);
      if (mainPlayerCl == null) {
        break missingId;
      }

      id = R.id.mini_player_singer_tv;
      TextView miniPlayerSingerTv = ViewBindings.findChildViewById(rootView, id);
      if (miniPlayerSingerTv == null) {
        break missingId;
      }

      id = R.id.mini_player_title_tv;
      TextView miniPlayerTitleTv = ViewBindings.findChildViewById(rootView, id);
      if (miniPlayerTitleTv == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, mainBnv, mainFrm,
          mainMiniplayerBtn, mainPauseBtn, mainPlayerCl, miniPlayerSingerTv, miniPlayerTitleTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

package com.treasure.parttimejob.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.ui.model.MsgBean;
import com.treasure.parttimejob.utils.GlideUtils;
import com.treasure.parttimejob.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ============================================================
 * Author：   treasure
 * time：  2019/3/7
 * description:
 * ============================================================
 */
public class MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private LayoutInflater inflater;
  private Context context;
  private List<MsgBean> list;

  public MsgAdapter(Context context, List<MsgBean> list) {
    this.context = context;
    this.list = list;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    View view;
    RecyclerView.ViewHolder holder = null;
    if (viewType == 0) {
      view = inflater.inflate(R.layout.layout_msg_top_view, viewGroup, false);
      holder = new HeadViewHolder(view);
    } else if (viewType == 1) {
      view = inflater.inflate(R.layout.layout_msg_item, viewGroup, false);
      holder = new MsgViewHolder(view);
    }
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
    if (position == 0) {
      onHeadViewHolder((HeadViewHolder) viewHolder);
    } else {
      MsgBean msgBean = list.get(position);
      onMsgViewHolder((MsgViewHolder) viewHolder, msgBean);
    }
  }

  private void onHeadViewHolder(HeadViewHolder viewHolder) {
    viewHolder.chat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ToastUtils.showAlertDialog(context);
      }
    });
    viewHolder.notice.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ToastUtils.showSingleToast("系统通知");
      }
    });
  }

  private void onMsgViewHolder(MsgViewHolder viewHolder, MsgBean msgBean) {
    viewHolder.name.setText(msgBean.getName());
    GlideUtils.loadCircleImg(context, msgBean.getImage(), viewHolder.image, R.mipmap.ic_launcher_logo);
    viewHolder.content.setText(msgBean.getContent());
  }

  @Override
  public int getItemCount() {
    if (list == null) return 0;
    return list.size();
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0){
      return 0;
    }else {
      return 1;
    }
  }

  public static class HeadViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.chat_layout)
    LinearLayout chat;
    @BindView(R.id.notice_layout)
    LinearLayout notice;

    public HeadViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  public static class MsgViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.content)
    TextView content;

    public MsgViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}

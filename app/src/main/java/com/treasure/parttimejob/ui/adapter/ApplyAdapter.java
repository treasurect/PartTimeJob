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

import com.treasure.parttimejob.PJApp;
import com.treasure.parttimejob.R;
import com.treasure.parttimejob.helper.Contacts;
import com.treasure.parttimejob.ui.activity.RatingActivity;
import com.treasure.parttimejob.ui.model.PartTimeBean;
import com.treasure.parttimejob.utils.GlideUtils;
import com.treasure.parttimejob.utils.SharedPreferencesUtil;
import com.treasure.parttimejob.utils.ToastUtils;
import com.treasure.parttimejob.utils.Utils;

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
public class ApplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private LayoutInflater inflater;
  private Context context;
  private List<PartTimeBean> list;
  private int type;

  public ApplyAdapter(Context context, List<PartTimeBean> list, int type) {
    this.context = context;
    this.list = list;
    this.type = type;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    View view = inflater.inflate(R.layout.layout_apply_item, viewGroup, false);
    return new MsgViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
    PartTimeBean partTimeBean = list.get(position);
    onMsgViewHolder((MsgViewHolder) viewHolder, partTimeBean);
  }

  private void onMsgViewHolder(MsgViewHolder viewHolder, final PartTimeBean partTimeBean) {
    viewHolder.partName.setText(partTimeBean.getPart_name());
    viewHolder.partPrice.setText(partTimeBean.getPart_money());
    viewHolder.partTag.setText(partTimeBean.getPart_clearing() + "/" + partTimeBean.getPart_duration() + "/" + partTimeBean.getPart_sex());
    GlideUtils.loadRoundImg(context, partTimeBean.getPart_image(), viewHolder.partImage, R.mipmap.ic_launcher_logo, 4);
    viewHolder.partBusiness.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Utils.callPhone(context,partTimeBean.getBusiness_phone());
      }
    });
    if (SharedPreferencesUtil.getInstance().getString(Contacts.USER_IDENTITY).equals("personal")){
      switch (type){
        case 0:
          viewHolder.partApply.setText("取消报名");
          break;
        case 1:
          viewHolder.partApply.setText("点击评价");
          break;
        case 2:
          viewHolder.partApply.setText("查看评价");
          break;
        case 3:
          viewHolder.partApply.setText("取消收藏");
          break;
      }
    }else {
      viewHolder.partBusiness.setVisibility(View.GONE);
      switch (type){
        case 0:
          viewHolder.partApply.setText("通过申请");
          break;
        case 1:
          viewHolder.partApply.setText("任务完成");
          break;
        case 2:
          viewHolder.partApply.setText("查看评价");
          break;
        case 3:
          viewHolder.partApply.setText("取消收藏");
          break;
      }
    }

    viewHolder.partApply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (SharedPreferencesUtil.getInstance().getString(Contacts.USER_IDENTITY).equals("personal")){
          switch (type){
            case 0:
              partTimeBean.setPart_apply(0);
              PJApp.userDaoManager.updatePart(partTimeBean);
              list.remove(partTimeBean);
              notifyDataSetChanged();
              ToastUtils.showSingleToast("已取消报名");
              break;
            case 1:
              RatingActivity.start(context,0,partTimeBean.getId());
              break;
            case 2:
              RatingActivity.start(context,1,partTimeBean.getId());
              break;
            case 3:
              partTimeBean.setPart_collect(false);
              PJApp.userDaoManager.updatePart(partTimeBean);
              list.remove(partTimeBean);
              notifyDataSetChanged();
              ToastUtils.showSingleToast("已取消收藏");
              break;
          }
        }else {
          switch (type){
            case 0:
              partTimeBean.setPart_apply(2);
              PJApp.userDaoManager.updatePart(partTimeBean);
              list.remove(partTimeBean);
              notifyDataSetChanged();
              ToastUtils.showSingleToast("已通过报名");
              break;
            case 1:
              partTimeBean.setPart_apply(3);
              PJApp.userDaoManager.updatePart(partTimeBean);
              list.remove(partTimeBean);
              notifyDataSetChanged();
              ToastUtils.showSingleToast("任务已完成");
              break;
            case 2:
              RatingActivity.start(context,1, partTimeBean.getId());
              break;
            case 3:
              partTimeBean.setPart_collect(false);
              PJApp.userDaoManager.updatePart(partTimeBean);
              list.remove(partTimeBean);
              notifyDataSetChanged();
              ToastUtils.showSingleToast("已取消收藏");
              break;
          }
        }
      }
    });
    viewHolder.root.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (onItemClickListener != null) {
          onItemClickListener.onItemClick(partTimeBean);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    if (list == null) return 0;
    return list.size();
  }

  public static class MsgViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.part_image)
    ImageView partImage;
    @BindView(R.id.part_name)
    TextView partName;
    @BindView(R.id.part_money)
    TextView partPrice;
    @BindView(R.id.part_tag)
    TextView partTag;
    @BindView(R.id.part_business)
    TextView partBusiness;
    @BindView(R.id.part_apply)
    TextView partApply;
    @BindView(R.id.root)
    LinearLayout root;

    public MsgViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  public interface OnItemClickListener {
    void onItemClick(PartTimeBean partTimeBean);
  }

  private OnItemClickListener onItemClickListener;

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}

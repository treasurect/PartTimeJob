package com.treasure.parttimejob.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.treasure.parttimejob.R;
import com.treasure.parttimejob.ui.activity.PartListActivity;
import com.treasure.parttimejob.ui.model.PartTimeBean;
import com.treasure.parttimejob.utils.GlideUtils;

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
public class PartTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private LayoutInflater inflater;
  private Context context;
  private List<PartTimeBean> list;
  private boolean isHomePage;

  public PartTimeAdapter(Context context, List<PartTimeBean> list, boolean isHomePage) {
    this.context = context;
    this.list = list;
    this.isHomePage = isHomePage;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    View view;
    RecyclerView.ViewHolder holder = null;
    if (viewType == 0) {
      view = inflater.inflate(R.layout.layout_home_top_view, viewGroup, false);
      holder = new HeadViewHolder(view);
    } else if (viewType == 1) {
      view = inflater.inflate(R.layout.layout_home_item, viewGroup, false);
      holder = new MsgViewHolder(view);
    }
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
    if (isHomePage && position == 0) {
      onHeadViewHolder((HeadViewHolder) viewHolder);
    } else {
      PartTimeBean partTimeBean = list.get(position);
      onMsgViewHolder((MsgViewHolder) viewHolder, partTimeBean);
    }
  }

  private void onHeadViewHolder(HeadViewHolder viewHolder) {
    viewHolder.location.setText("北京");
    viewHolder.longPart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        PartListActivity.start(context,"长期");
      }
    });
    viewHolder.shortPart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        PartListActivity.start(context,"短期");
      }
    });
    viewHolder.skillPart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        PartListActivity.start(context,"技能");
      }
    });
    viewHolder.interPart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        PartListActivity.start(context,"实习");
      }
    });
  }

  private void onMsgViewHolder(MsgViewHolder viewHolder, final PartTimeBean partTimeBean) {
    viewHolder.title.setText(partTimeBean.getPart_name());
    viewHolder.price.setText(partTimeBean.getPart_money());
    viewHolder.tag.setText(partTimeBean.getPart_clearing() + "/" + partTimeBean.getPart_duration() + "/" + partTimeBean.getPart_sex());
    GlideUtils.loadRoundImg(context, partTimeBean.getPart_image(), viewHolder.image, R.mipmap.ic_launcher_logo,4);
    viewHolder.address.setText(partTimeBean.getPart_address());
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

  @Override
  public int getItemViewType(int position) {
    if (isHomePage && position == 0) {
      return 0;
    } else {
      return 1;
    }
  }

  public static class HeadViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.now_loc)
    TextView location;
    @BindView(R.id.long_part)
    RelativeLayout longPart;
    @BindView(R.id.short_part)
    RelativeLayout shortPart;
    @BindView(R.id.skill_part)
    RelativeLayout skillPart;
    @BindView(R.id.inter_part)
    RelativeLayout interPart;
    @BindView(R.id.intent_setting)
    TextView intentSet;

    public HeadViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  public static class MsgViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.tag)
    TextView tag;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.root)
    FrameLayout root;

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

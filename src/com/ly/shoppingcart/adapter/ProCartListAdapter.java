package com.ly.shoppingcart.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.os.RecoverySystem.ProgressListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ly.shoppingcart.R;
import com.ly.shoppingcart.activity.ProCartListActivity;
import com.ly.shoppingcart.bean.ProCartListBean;
import com.ly.shoppingcart.config.BaseUrl;
import com.ly.shoppingcart.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ProCartListAdapter extends CommonAdapter<ProCartListBean> {
	//被选中商品 的数量

	private DecimalFormat decimalFormat;
	
	private ProCartListActivity activity;
	
    TextView numberTv;
    TextView subTotalTv;


	public ProCartListAdapter(Context context, List<ProCartListBean> list,int layoutId,ProCartListActivity activity) {
		super(context, list, layoutId);
		this.activity = activity;
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
		decimalFormat = new DecimalFormat("0.00");
	}
	
	public void setResult(List<ProCartListBean> result){
		list = result;
	}
	
	@Override
	public void convert(final ViewHolder viewHolder, final ProCartListBean t,int position) {
	  final int number = t.number;
	  final float subTotal = t.subTotal;
	  boolean isSelect = t.isSelect;
	  viewHolder.setText(R.id.tv_title, t.title)
				.setText(R.id.tv_price, "批发价：" + t.price + "￥")
				.setText(R.id.tv_pinpai, "品牌：" + t.brand)
				.setText(R.id.tv_item, "订货号：" + t.item)
				.setText(R.id.tv_xinghao, "型号：" + t.model)
				.setText(R.id.tv_pro_num,number+"")
				.setText(R.id.tv_item_price, "小计: " + decimalFormat.format(subTotal));
	  			//对加减号监听
	            setAddAndSubListener(viewHolder,t);
	            //加载图片
	            loadImage(viewHolder,t);
	            //删除商品监听
	            setDeleteListener(viewHolder,t,position);
	            CheckBox checkBox = viewHolder.getView(R.id.cb_select);
	            //每个商品选项的监听
	            setCheckListener(viewHolder,checkBox,position);
			    checkBox.setChecked(isSelect);
	}
	
	/**
	 * 每个商品选项的监听
	 * @param viewHolder
	 * @param checkBox
	 * @param subTotal
	 */
	private void setCheckListener(final ViewHolder viewHolder,final CheckBox checkBox,final int position) {
		checkBox.setOnClickListener(activity);
		checkBox.setTag(position);
	}

	/**
	 * 删除商品监听
	 * @param viewHolder
	 * @param t
	 */
	private void setDeleteListener(final ViewHolder viewHolder, final ProCartListBean t,final int position) {
		ImageView imageBt = viewHolder.getView(R.id.iv_delete);
		imageBt.setOnClickListener(activity);
		imageBt.setTag(position);
	}

	/**
	 * 加载图片
	 * @param viewHolder
	 * @param t
	 */
	private void loadImage(ViewHolder viewHolder, ProCartListBean t) {
		ImageView imageView = viewHolder.getView(R.id.iv_goods);
		ImageLoader.getInstance().displayImage(BaseUrl.EHSY_BASE_URL + t.thumb,imageView, baseOptions);
		
	}

	/**
	 * 对加减号监听
	 * @param viewHolder
	 * @param t
	 * @param number
	 */
	private void setAddAndSubListener(final ViewHolder viewHolder, final ProCartListBean t) {
	    TextView addTv = viewHolder.getView(R.id.tv_add_price);
	    TextView subTv = viewHolder.getView(R.id.tv_sub_price);
	    numberTv = viewHolder.getView(R.id.tv_pro_num);
		subTotalTv = viewHolder.getView(R.id.tv_item_price);
	    final int position = viewHolder.getPosition();
	    addTv.setOnClickListener(activity);
	    addTv.setTag(position);
	    subTv.setOnClickListener(activity);
	    subTv.setTag(position);
	}
	
	public List<ProCartListBean>getProList(){
		return list;
	}
}

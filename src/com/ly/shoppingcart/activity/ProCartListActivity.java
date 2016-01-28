package com.ly.shoppingcart.activity;
import java.text.DecimalFormat;
import java.util.List;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.ly.shoppingcart.R;
import com.ly.shoppingcart.adapter.ProCartListAdapter;
import com.ly.shoppingcart.bean.ProCartListBean;
import com.ly.shoppingcart.bean.completeUrl;
import com.ly.shoppingcart.config.FlagConfig;
import com.ly.shoppingcart.dialog.Mydialog;
import com.ly.shoppingcart.http.BaseLoad;
import com.ly.shoppingcart.http.HttpDeletefromCart;
import com.ly.shoppingcart.http.HttpLoadProcartList;
import com.ly.shoppingcart.http.HttpUpdateToCart;
import com.ly.shoppingcart.myinterface.OnLoadListener;
import com.ly.shoppingcart.utils.Calculate;
import com.ly.shoppingcart.utils.DialogWindow;
import com.ly.shoppingcart.utils.MyThreadPool;
import com.ly.shoppingcart.utils.NetUtil;
import com.ly.shoppingcart.view.CustomTitleBarView;
import com.ly.shoppingcart.view.CustomTitleBarView.onBarViewClickListener;
public class ProCartListActivity extends Activity implements OnClickListener,
		OnLoadListener, DialogInterface.OnClickListener, onBarViewClickListener {
	    //已经选择的商品数量
		private TextView alreadySelectTv;
		//商品的总价
		private TextView allPriceTv;
		//是否全选
		private boolean isSelectAll = false;
		private CheckBox allSelectCb;
		private ProCartListAdapter adapter;
		private ListView listView;
		//当前操作商品位置
		private int position;
		private CustomTitleBarView customTitleBarView;
		//商品的总价格
		 private float all = 0.0f;
		//被选中商品的数量
		private int selectCount = 0;
		private DecimalFormat decimalFormat;
		private ImageView proNullImage;
		private DialogWindow dialogWindow;
		private boolean noRepeat = true;
		private Mydialog.Builder builder;
		private boolean isAdd = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pro_cart_list);
		initView();
		initDatas();
		initEvent();
		loadContent();
	}
	private void initView() {
		listView = (ListView)findViewById(R.id.lv);
		allSelectCb = (CheckBox)findViewById(R.id.iv_all_select);
		alreadySelectTv = (TextView)findViewById(R.id.tv_select_count);
		allPriceTv = (TextView) findViewById(R.id.tv_all_price);
		proNullImage = (ImageView) findViewById(R.id.pro_null);
		customTitleBarView = (CustomTitleBarView) findViewById(R.id.cTitleBar);
	}
	private void initDatas() {
		decimalFormat = new DecimalFormat("0.00");
		adapter = new ProCartListAdapter(ProCartListActivity.this, null, R.layout.item_pro_cart_list,this);
		dialogWindow = new DialogWindow(this);
		builder = new Mydialog.Builder(this);
	}
	private void initEvent() {
		allSelectCb.setOnClickListener(this);
		customTitleBarView.setOnBarViewClickListener(this);
	}
	private void loadContent() {
		if (NetUtil.isNetworkAvailable(this)) {
			showBuyProList("加载中");
		}else{
			Toast.makeText(this,"请检查网络",Toast.LENGTH_SHORT).show();
		}
	}
    /**
     * 展示已选商品列表
     * @param string
     */
	private void showBuyProList(String string) {
		builder.showDialogLoading(dialogWindow.getLoadingDilogWidth(),dialogWindow.getLoadingDialogHeight(), "加载中...");
		BaseLoad baseLoad = new HttpLoadProcartList(completeUrl.getProCartUrl());
		MyThreadPool.getInstance().ExecuteMyThread(baseLoad);
		baseLoad.setOnLoadListener(this);
	}
	@Override
	public void onLoadSuccess(Object success, int flag) {
		switch (flag) {
		case FlagConfig.CART_LIST:
			List<ProCartListBean>result = (List<ProCartListBean>) success;
			if (result.size()==0) {
				changeControlState();
			}else{
				adapter.setResult(result);
				listView.setAdapter(adapter);
			}
			break;
		case FlagConfig.PRO_UPDATE:
			changeNumber(position,adapter.getItem(position).isSelect,isAdd);
			builder.dismissMyDialog();
			adapter.notifyDataSetChanged();
			noRepeat = true;
			break;
		case FlagConfig.PRO_DELETE:
			removeItem(position);
			 if (selectCount==adapter.getCount()) {
				 if (selectCount==0) {
					 changeControlState();
					}else{
						allSelectCb.setChecked(true);
					}
			}else{
				allSelectCb.setChecked(false);
			}
			 setProDatas();
			builder.dismissMyDialog();
			Toast.makeText(ProCartListActivity.this, (String)success, Toast.LENGTH_SHORT).show();
			break;
		}
		builder.dismissMyDialog();
	}
	@Override
	public void onLoadFailed(Object failed) {
		    builder.dismissMyDialog();
		    noRepeat = true;
			Toast.makeText(this, (String)failed, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//全选
		case R.id.iv_all_select:{
			if (isSelectAll&&adapter.getProList()!=null) {
				unSelectAllPro();
				isSelectAll = false;
			} else if (!isSelectAll&&adapter.getProList()!=null) {
				selectAllPro();
				isSelectAll = true;
			}
			setProDatas();
			adapter.notifyDataSetChanged();
			break;
		}
		//删除
		case R.id.iv_delete:
			builder.showDialogAlert(dialogWindow.getAlertDilogWidth(), dialogWindow.getAlertDialogHeight(),null, "确定删除该商品吗?");
			position = (Integer) v.getTag();
			break;
			//加号按钮
		case R.id.tv_add_price:{
			position = (Integer) v.getTag();
			if (noRepeat) {
				noRepeat = false;
				isAdd = true;
				UpdateItem();
			}
			break;
		}
		//减号按钮
        case R.id.tv_sub_price:{
    		position = (Integer) v.getTag();
        	ProCartListBean bean = adapter.getItem(position);
        	int number = bean.number;
            	if (number>1&&noRepeat) {
            		noRepeat = false;
            		isAdd = false;
            		UpdateItem();
				}
			break;
			}
        //checkbox选择
        case R.id.cb_select:
        	int position = (Integer) v.getTag();
        	CheckBox checkBox = (CheckBox) v;
        	ProCartListBean bean = adapter.getItem(position);
        	bean.isSelect = checkBox.isChecked();
        	isProSelect(position,checkBox.isChecked());
        	break;
		}
	}
	@Override
	public void onBarViewClick(View v, int witch) {
		switch (witch) {
		case CustomTitleBarView.RIGHT:
			Toast.makeText(ProCartListActivity.this,"right", Toast.LENGTH_SHORT).show();
			break;
        case CustomTitleBarView.LEFT:
			Toast.makeText(ProCartListActivity.this,"left", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		//确定删除
		case Mydialog.Builder.SURE:
			deleteProFromCart();
			dialog.dismiss();
			break;
		}
		dialog.dismiss();
	}
	/**
	 * 从购物车里删除商品
	 * @param dialogInfo
	 * @param position
	 * @param url
	 * @param adapter
	 */
	public void deleteProFromCart() {
		builder.showDialogLoading(dialogWindow.getLoadingDilogWidth(),dialogWindow.getLoadingDialogHeight(), "删除中...");
		ProCartListBean bean = adapter.getItem(position);
		BaseLoad baseLoad = new HttpDeletefromCart(completeUrl.getDeleteUrl(bean.item));
		MyThreadPool.getInstance().ExecuteMyThread(baseLoad);
		baseLoad.setOnLoadListener(this);
   }
	/**
	 * 更新商品
	 * @param number
	 * @param item
	 */
	public void UpdateItem(){
		builder.showDialogLoading(dialogWindow.getLoadingDilogWidth(),dialogWindow.getLoadingDialogHeight(), "更新中...");
		ProCartListBean bean = adapter.getItem(position);
		int number = bean.number;
		String item = bean.item;
		number = isAdd==true?++number:--number;
		BaseLoad baseLoad = new HttpUpdateToCart(completeUrl.getUpdateUrl(number, item));
		MyThreadPool.getInstance().ExecuteMyThread(baseLoad);
		baseLoad.setOnLoadListener(this);
    }
	/**
	 * 点击甲减好改变number值
	 * @param position
	 * @param isSelect
	 * @param isAdd
	 */
	public void changeNumber(int position,boolean isSelect,boolean isAdd){
		ProCartListBean bean = adapter.getItem(position);
		if (isAdd) {
			bean.number++;
		}else{
			bean.number--;
		}
		changeSubtotal(position,isSelect,isAdd);
	}
	/**
	 * 点击加减号改变小计的值
	 * @param position
	 * @param isSelect
	 * @param isAdd
	 */
	public void changeSubtotal(int position,boolean isSelect,boolean isAdd){
		ProCartListBean bean = adapter.getItem(position);
			float subTotal = bean.subTotal;
			float price = bean.price;
			if (isAdd) {
				bean.subTotal=Calculate.decimaAdd(subTotal, price);
			}else if (!isAdd){
				bean.subTotal=Calculate.decimaSub(subTotal, price);
			}
			if (isSelect) {
				changePro(position,isAdd);
			}
	}
	/**
	 * 点击加减号改变总计和count的值
	 * @param position
	 * @param isSelect
	 * @param isAdd
	 */
	public void changePro(int position,boolean isAdd){
		ProCartListBean bean = adapter.getItem(position);
		float price = bean.price;
		if (isAdd) {
			all = Calculate.decimaAdd(all, price);
		}else if (!isAdd){
			all = Calculate.decimaSub(all, price);
		}
		setProDatas();
	}
	/**
	 * sku删除选项
	 */
	private void removeItem(int pos) {
		ProCartListBean bean = adapter.getItem(pos);
		boolean isSelect = bean.isSelect;
		float subTotal = bean.subTotal;
		if (isSelect) {
			selectCount--;
			all = Calculate.decimaSub(all, subTotal);
		}
		adapter.getProList().remove(pos);
		adapter.notifyDataSetChanged();
	}
	/**
	 * 点击checkBox时候所做出的处理
	 * @param position
	 * @param checked
	 */
	private void isProSelect(int position, boolean checked) {
		ProCartListBean bean = adapter.getItem(position);
		float subTotal = bean.subTotal;
		if (checked) {
			selectCount++;
			all = Calculate.decimaAdd(all, subTotal);
		}else{
			selectCount--;
			if (selectCount==0) {
				all = 0.00f;
			}else{
				all = Calculate.decimaSub(all, subTotal);
			}
		}
        if (adapter.getCount()== selectCount) {
        	allSelectCb.setChecked(true);
			isSelectAll = true;
		} else {
			allSelectCb.setChecked(false);
			isSelectAll = false;
		}
        setProDatas();
		bean.isSelect = checked;
	}
	/**
	 * 选中所有商品
	 */
	private void selectAllPro() {
		resetAllValue(1);
		selectCount = adapter.getCount();
	}
	/**
	 * 取消选中所有商品
	 */
	private void unSelectAllPro() {
		resetAllValue(0);
		selectCount = 0;
	}
	/**
	 * 重置总价格的值
	 * @param type
	 */
	private void resetAllValue(int type){
		if (type==0) {
			for (int i = 0; i < adapter.getProList().size(); i++) {
				if (adapter.getProList().get(i).isSelect) {
					adapter.getProList().get(i).isSelect=false;
				}
			}
			all=0;
		}else{
			for (int i = 0; i < adapter.getProList().size();i++) {
				float subTotal = adapter.getProList().get(i).subTotal;
				if (!adapter.getProList().get(i).isSelect) {
					adapter.getProList().get(i).isSelect=true;
					all = Calculate.decimaAdd(all, subTotal);
				}
			}
		}
	}
	/**
	 * 改变控件的状态
	 */
	private void changeControlState(){
		listView.setVisibility(View.GONE);
		proNullImage.setVisibility(View.VISIBLE);
		allSelectCb.setClickable(false);
		allSelectCb.setFocusable(false);
		allSelectCb.setChecked(false);
	}
	private void setProDatas(){
		allPriceTv.setText("总计：" + decimalFormat.format(all) + "￥");
		alreadySelectTv.setText("已选商品：" + selectCount);
	}
}

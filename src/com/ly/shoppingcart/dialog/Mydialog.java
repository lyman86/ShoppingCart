package com.ly.shoppingcart.dialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ly.shoppingcart.R;

public class Mydialog extends Dialog {
	
	public Mydialog(Context context) {
		super(context);
	}

	public Mydialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context mContext;
		private String mTitle;
		private String mMessage;
		private String mPositiveBtnText;
		private String mNagetiveBtnText;

		private TextView messageTv;
		private TextView titleTv;

		private Button sureBtn;
		private Button cancleBtn;
		private LinearLayout btnLayou;
		private ProgressBar progressBar;
		private Mydialog dialog;
		private View layout;
		
		private static final int GONE = 0;
		private static final int VISIBLE = 1;

		public  int mWidth = 600;
		private  int mHeight = 300;

		private DialogInterface.OnClickListener mPositiveBtnOnclickListener;
		private DialogInterface.OnClickListener mNagetiveBtnOncclickListener;
		
		public volatile boolean  state = false;
		//确定删除
		public static final int SURE = -1;

		public Builder(Context context) {
			mContext = context;
			
		}

		/**
		 * 直接给字符串设置消息
		 * 
		 * @param msg
		 * @return
		 */
		public Builder setMessage(String msg) {
			mMessage = msg;
			return this;
		}

		/**
		 * 从资源文件中获取设置消息
		 * 
		 * @param msg
		 * @return
		 */
		public Builder setMessage(int msg) {
			mMessage = (String) mContext.getText(msg);
			return this;
		}

		/**
		 * 直接给字符串设置标题
		 * 
		 * @param msg
		 * @return
		 */
		public Builder setTitle(String title) {
			mTitle = title;
			return this;
		}

		/**
		 * 从资源文件中获取设置标题
		 * 
		 * @param msg
		 * @return
		 */
		public Builder setTitle(int title) {
			mTitle = (String) mContext.getText(title);
			return this;
		}

		/**
		 * 设置positiveButton的文字和监听
		 * 
		 * @param positiveBtnText
		 *            文字直接给字符串
		 * @param positiveBtnListener
		 * @return
		 */
		public Builder setPositiveButton(String positiveBtnText,
				DialogInterface.OnClickListener positiveBtnListener) {
			mPositiveBtnOnclickListener = positiveBtnListener;
			mPositiveBtnText = positiveBtnText;
			return this;
		}

		/**
		 * 设置positiveButton的文字和监听
		 * 
		 * @param positiveBtnText
		 *            文字从资源文件中获取
		 * @param positiveBtnListener
		 * @return
		 */
		public Builder setPositiveButton(int positiveBtnRes,
				DialogInterface.OnClickListener positiveBtnListener) {
			mPositiveBtnOnclickListener = positiveBtnListener;
			mPositiveBtnText = (String) mContext.getText(positiveBtnRes);
			return this;
		}

		/**
		 * 设置positiveButton的文字和监听
		 * 
		 * @param positiveBtnText
		 *            文字直接给字符串
		 * @param positiveBtnListener
		 * @return
		 */
		public Builder setNagetiveButton(String nagetiveBtnText,
				DialogInterface.OnClickListener nagetiveBtnListener) {
			mNagetiveBtnOncclickListener = nagetiveBtnListener;
			mNagetiveBtnText = nagetiveBtnText;
			return this;
		}

		/**
		 * 设置positiveButton的文字和监听
		 * 
		 * @param positiveBtnText
		 *            文字从资源文件中获取
		 * @param positiveBtnListener
		 * @return
		 */
		public Builder setNagetiveButton(int nagetiveBtnRes,
				DialogInterface.OnClickListener nagetiveBtnListener) {
			mNagetiveBtnOncclickListener = nagetiveBtnListener;
			mNagetiveBtnText = (String) mContext.getText(nagetiveBtnRes);
			return this;
		}

		public Builder setDialogWidthAndHeight(int width, int height) {
			mWidth = width;
			mHeight = height;
			return this;

		}

		public Mydialog create() {
			state = true;
			setDialogVIew();

			return dialog;
		}

		public void showDialogLoading(int width, int height,String message) {
			setMessage(message);
			setDialogWidthAndHeight(width, height);
			mPositiveBtnText = null;
			mNagetiveBtnText = null;
			init();
			setProgressbarState(VISIBLE);
			create().show();
		}

		public void showDialogAlert(int width, int height, String title,String message) {
			
			
			setDialogWidthAndHeight(width, height);
			setMessage(message);
			setTitle(title);
			
			setPositiveButton("确定", (OnClickListener) mContext);
			setNagetiveButton("取消", (OnClickListener) mContext);
			init();
			setProgressbarState(GONE);
			create().show();
		}

		public void setProgressbarState(int state) {
			switch (state) {
			// 隐藏
			case GONE:
				progressBar.setVisibility(View.GONE);
				break;
			//显示
			case VISIBLE:
				progressBar.setVisibility(View.VISIBLE);
				break;

			}
		}
		
		public void dismissMyDialog(){
			state = false;
			dialog.dismiss();
		}

		private void setDialogVIew() {
			dialog.getWindow().setLayout(mWidth, mHeight);
			dialog.setContentView(layout);

		}
		
		private void init() {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			dialog = new Mydialog(mContext, R.style.Dialog);
			layout = inflater.inflate(R.layout.my_dialog, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			dialog.setCanceledOnTouchOutside(false);
			initView();
			
		}

		private void initView() {

			messageTv = (TextView) layout.findViewById(R.id.tv_message);
			titleTv = (TextView) layout.findViewById(R.id.tv_title);

			sureBtn = ((Button) layout.findViewById(R.id.bt_sure));
			cancleBtn = ((Button) layout.findViewById(R.id.bt_cancle));
			btnLayou = (LinearLayout) layout.findViewById(R.id.btn_llayout);
			progressBar = (ProgressBar) layout.findViewById(R.id.progressbar);
			if (mMessage == null) {
				messageTv.setVisibility(View.GONE);
			} else {
				messageTv.setText(mMessage);
			}

			if (mTitle == null) {
				titleTv.setVisibility(View.GONE);
			} else {
				titleTv.setText(mTitle);
			}
			if (mPositiveBtnText == null) {
				sureBtn.setVisibility(View.GONE);
			} else {
				sureBtn.setText(mPositiveBtnText);
			}

			if (mNagetiveBtnText == null) {
				cancleBtn.setVisibility(View.GONE);
			} else {
				cancleBtn.setText(mNagetiveBtnText);
			}

			if (mPositiveBtnText == null && mNagetiveBtnText == null) {
				btnLayou.setVisibility(View.GONE);
			}

			initEvent();

		}

		private void initEvent() {
			if (mPositiveBtnOnclickListener != null) {
				sureBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						mPositiveBtnOnclickListener.onClick(dialog,
								DialogInterface.BUTTON_POSITIVE);
					}
				});
			}
			if (mNagetiveBtnOncclickListener != null) {
				cancleBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						mNagetiveBtnOncclickListener.onClick(dialog,
								DialogInterface.BUTTON_NEGATIVE);
					}
				});
			}
		}
		
		

	}

	

}

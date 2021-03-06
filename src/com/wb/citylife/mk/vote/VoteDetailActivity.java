﻿package com.wb.citylife.mk.vote;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.common.net.volley.VolleyErrorHelper;
import com.common.widget.ToastHelper;
import com.viewpagerindicator.CirclePageIndicator;
import com.wb.citylife.R;
import com.wb.citylife.activity.base.BaseActivity;
import com.wb.citylife.activity.base.ReloadListener;
import com.wb.citylife.adapter.VoteAdapter;
import com.wb.citylife.app.CityLifeApp;
import com.wb.citylife.bean.VoteDetail;
import com.wb.citylife.config.IntentExtraConfig;
import com.wb.citylife.config.NetConfig;
import com.wb.citylife.config.NetInterface;
import com.wb.citylife.config.RespCode;
import com.wb.citylife.mk.common.CommIntent;
import com.wb.citylife.task.VoteDetailRequest;
import com.wb.citylife.widget.TouchControllViewPager;

public class VoteDetailActivity extends BaseActivity implements Listener<VoteDetail>, 
	ErrorListener, ReloadListener, OnClickListener{
	
	private TextView voteTitleTv;
	private ViewGroup itemVg;
	private NetworkImageView imgIv;
	private TextView descTv;
	private TextView timeTv;
	private TextView numTv;
	private TouchControllViewPager mVotePager;
	private Button mSubmitBtn;
	private CirclePageIndicator mVoteIndicator;
	
	private String voteId;
	
	//投票详情
	private VoteDetailRequest mVoteDetailRequest;
	private VoteDetail mVoteDetail;
	private VoteAdapter mVoteAdapter;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_votedetail);
		
		getIntentData();
		initView();		
		
		showLoading();
	}
			
	@Override
	public void getIntentData() {
		voteId = getIntent().getStringExtra(IntentExtraConfig.DETAIL_ID);
	}
	
	@Override
	public void initView() {
		voteTitleTv = (TextView) findViewById(R.id.vote_title);
		itemVg = (ViewGroup) findViewById(R.id.news_item);
		itemVg.setOnClickListener(this);
		imgIv = (NetworkImageView) findViewById(R.id.img);
		descTv = (TextView) findViewById(R.id.desc);
		timeTv = (TextView) findViewById(R.id.time);
		numTv = (TextView) findViewById(R.id.num);
		mVotePager = (TouchControllViewPager) findViewById(R.id.vote_pager);
		mVoteIndicator = (CirclePageIndicator) findViewById(R.id.vote_indicator);
		mSubmitBtn = (Button) findViewById(R.id.submit);			
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//此处设置菜单		
		setDisplayHomeAsUpEnabled(true);
		setDisplayShowHomeEnabled(false);
		
		requestVoteDetail(Method.POST, NetInterface.METHOD_VOTE_DETAIL, getVoteDetailRequestParams(), this, this);		
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * 菜单点击处理
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {			
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		
		switch(v.getId()) {
		case R.id.news_item:
			CommIntent.startDetailPage(this, mVoteDetail.id, mVoteDetail.type);
			break;
		}
	}
		
	/**
	 * 获取详情请求参数
	 * @return
	 */
	private Map<String, String> getVoteDetailRequestParams() {
		Map<String, String> params = new HashMap<String, String>();
		if(CityLifeApp.getInstance().checkLogin()) {
			params.put("userId", CityLifeApp.getInstance().getUser().userId);
		}
		params.put("id", voteId);
		params.put("phoneId", CityLifeApp.getInstance().getPhoneId());
		return params;
	}
	
	/**
	 * 执行详情任务请求
	 * @param method
	 * @param url
	 * @param params
	 * @param listenre
	 * @param errorListener
	 */	
	private void requestVoteDetail(int method, String methodUrl, Map<String, String> params,	 
			Listener<VoteDetail> listenre, ErrorListener errorListener) {			
		if(mVoteDetailRequest != null) {
			mVoteDetailRequest.cancel();
		}	
		String url = NetConfig.getServerBaseUrl() + NetConfig.EXTEND_URL + methodUrl;
		mVoteDetailRequest = new VoteDetailRequest(method, url, params, listenre, errorListener);
		startRequest(mVoteDetailRequest);		
	}
	
	/**
	 * 网络请求错误处理
	 */
	@Override
	public void onErrorResponse(VolleyError error) {	
		showLoadError(this);
		ToastHelper.showToastInBottom(getApplicationContext(), VolleyErrorHelper.getErrorMessage(error));
	}
	
	/**
	 * 加载失败重，点击重新加载的处理
	 */
	@Override
	public void onReload() {
		requestVoteDetail(Method.POST, NetInterface.METHOD_VOTE_DETAIL, getVoteDetailRequestParams(), this, this);
		showLoading();
	}
	
	/**
	 * 请求详情完成，处理UI更新
	 */
	@Override
	public void onResponse(VoteDetail response) {		
		mVoteDetail = response;
		if(mVoteDetail.respCode == RespCode.SUCCESS) {			
			voteTitleTv.setText(mVoteDetail.title);
			imgIv.setImageUrl(mVoteDetail.thumbnailUrl, CityLifeApp.getInstance().getImageLoader());
			imgIv.setDefaultImageResId(R.drawable.base_list_default_icon);
			descTv.setText(mVoteDetail.summary);
			timeTv.setText(mVoteDetail.time);
			numTv.setText(mVoteDetail.participantNum + "人参与");		
			
			mVoteAdapter = new VoteAdapter(this, mVotePager, mVoteIndicator, mSubmitBtn, mVoteDetail, voteId);
			mVotePager.setAdapter(mVoteAdapter);
			mVoteIndicator.setViewPager(mVotePager);
			
			showContent();
		} 
	}			
}

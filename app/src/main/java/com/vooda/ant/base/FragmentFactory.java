package com.vooda.ant.base;

import com.vooda.ant.ui.fragment.CartFragment;
import com.vooda.ant.ui.fragment.HomeFragment;
import com.vooda.ant.ui.fragment.MeFragment;
import com.vooda.ant.ui.fragment.OrderFragment;
import com.vooda.ant.ui.fragment.market.PriceFragment;
import com.vooda.ant.ui.fragment.market.FiltrateFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc: TODO
 * @author: zhujun
 * @date: 2016/12/21 0021  下午 3:09
 */
public class FragmentFactory {
    public static final int FRAGMENT_HOME  = 0;
    public static final int FRAGMENT_ORDER = 1;
    public static final int FRAGMENT_CART  = 2;
    public static final int FRAGMENT_ME    = 3;

    private static Map<Integer, RxLazyFragment> fragments = new HashMap<>();


    public static final int FRAGMENT_MARKET_PRICE = 4;
    public static final int FRAGMENT_MARKET_FILTRATE = 5;
    private static Map<Integer, RxLazyFragment> marketFragments = new HashMap<>();


    public static RxLazyFragment getFragment(int position) {
        RxLazyFragment fragment = null;
        if (fragments.containsKey(position)) {
            RxLazyFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        switch (position) {
            case FRAGMENT_HOME:
                fragment = new HomeFragment();       //首页
                break;
            case FRAGMENT_ORDER:
                fragment = new OrderFragment();  //订单
                break;
            case FRAGMENT_CART:
                fragment = new CartFragment();  //购物车
                break;
            case FRAGMENT_ME:
                fragment = new MeFragment();  //个人中心
                break;
            default:
                break;
        }
        fragments.put(position, fragment);
        return fragment;
    }

    public static RxLazyFragment getMarketFragment(int position) {
        RxLazyFragment fragment = null;
        if (marketFragments.containsKey(position)) {
            RxLazyFragment baseFragment = marketFragments.get(position);
            return baseFragment;
        }
        switch (position) {
            case FRAGMENT_MARKET_PRICE:
                fragment = new PriceFragment();       //综合，价格
                break;
            case FRAGMENT_MARKET_FILTRATE:
                fragment = new FiltrateFragment();  //筛选
                break;
            default:
                break;
        }
        marketFragments.put(position, fragment);
        return fragment;
    }

}

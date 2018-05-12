package com.BBsRs.horoscopeNewEdition;

import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.Addons;
import org.holoeverywhere.slider.SliderMenu;

import android.os.Bundle;

import com.BBsRs.horoscopeNewEdition.Base.BaseActivity;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Fragments.ContentFragment;
import com.BBsRs.horoscopeNewEdition.Fragments.SettingsFragment;


@Addons(AddonSlider.class)
public class ContentActivity extends BaseActivity {
	public AddonSlider.AddonSliderA addonSlider() {
		return addon(AddonSlider.class);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setIcon(R.drawable.ic_menu);
        
		final SliderMenu sliderMenu = addonSlider().obtainDefaultSliderMenu(R.layout.menu);
		sliderMenu.setInverseTextColorWhenSelected(false);
        addonSlider().setOverlayActionBar(true);
        
        //init bundles
        Bundle yesterday  = new Bundle();
        yesterday.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[0]);
        yesterday.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_YESTERDAY);
        Bundle today  = new Bundle();
        today.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[1]);
        today.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_TODAY);
        Bundle tomorrow  = new Bundle();
        tomorrow.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[2]);
        tomorrow.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_TOMORROW);
        Bundle weekly  = new Bundle();
        weekly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[3]);
        weekly.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_WEEKLY);
        Bundle monthly  = new Bundle();
        monthly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[4]);
        monthly.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_MONTHLY);
        Bundle yearly  = new Bundle();
        yearly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[5]);
        yearly.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_YEARLY);
        Bundle settings  = new Bundle();
        settings.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getString(R.string.settings));
        Bundle about  = new Bundle();
        about.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getString(R.string.about));
		
		sliderMenu.add(getResources().getString(R.string.app_name_to_upper_case)).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[0], ContentFragment.class, yesterday, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[1], ContentFragment.class, today, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[2], ContentFragment.class, tomorrow, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[3], ContentFragment.class, weekly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[4], ContentFragment.class, monthly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[5], ContentFragment.class, yearly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getString(R.string.settings), SettingsFragment.class, settings, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_settings).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getString(R.string.application_to_upper_case)).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
		sliderMenu.add(getResources().getString(R.string.about), ContentFragment.class, about, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_about).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		
		//set curent page to mainFragment only if fisrt launch Activity
		if ((savedInstanceState == null))
			sliderMenu.setCurrentPage(2);
    }
}

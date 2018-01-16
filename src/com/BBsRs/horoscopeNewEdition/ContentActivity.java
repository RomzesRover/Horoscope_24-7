package com.BBsRs.horoscopeNewEdition;

import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.Addons;
import org.holoeverywhere.slider.SliderMenu;

import android.os.Bundle;

import com.BBsRs.horoscopeNewEdition.Base.BaseActivity;
import com.BBsRs.horoscopeNewEdition.Fragments.ContentFragment;


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
		
		sliderMenu.add(getResources().getString(R.string.app_name_to_upper_case)).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[0], ContentFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[1], ContentFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[2], ContentFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[3], ContentFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_personal).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[4], ContentFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[5], ContentFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[6], ContentFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getString(R.string.settings), ContentFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_settings).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getString(R.string.application_to_upper_case)).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
		sliderMenu.add(getResources().getString(R.string.about), ContentFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_about).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		
		//set curent page to mainFragment only if fisrt launch Activity
		if ((savedInstanceState == null))
			sliderMenu.setCurrentPage(1);
    }
}

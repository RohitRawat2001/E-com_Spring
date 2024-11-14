package com.Ecom.service;

import com.Ecom.modals.Home;
import com.Ecom.modals.HomeCategory;

import java.util.List;

public interface HomeService {
    Home createHomePageData(List<HomeCategory> allCategories);
}

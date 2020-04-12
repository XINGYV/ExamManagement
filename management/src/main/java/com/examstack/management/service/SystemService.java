package com.examstack.management.service;

import com.exammanagement.common.util.MenuItem;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public interface SystemService {

    public LinkedHashMap<String, MenuItem> getMenuItemsByAuthority(String authority);
}

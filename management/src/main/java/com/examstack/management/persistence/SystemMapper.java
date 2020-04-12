package com.examstack.management.persistence;

import com.exammanagement.common.util.MenuItem;

import java.util.List;


public interface SystemMapper {

    public List<MenuItem> getMenuItemsByAuthority(String authority);
}

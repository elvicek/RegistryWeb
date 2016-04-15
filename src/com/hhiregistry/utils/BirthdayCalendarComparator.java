package com.hhiregistry.utils;

import java.util.Comparator;

import com.hhiregistry.local.model.BirthdayCalendar;

public class BirthdayCalendarComparator implements Comparator{

	@Override
	public int compare(Object obj1, Object obj2) {
		BirthdayCalendar cal1 = (BirthdayCalendar)obj1;
		BirthdayCalendar cal2 = (BirthdayCalendar)obj2;
		
		return(cal1.dueTime - cal2.dueTime);
		
	}

	
	
}

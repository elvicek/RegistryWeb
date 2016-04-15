package com.hhiregistry.local.model;

import java.util.Date;

import org.joda.time.DateTime;

import com.hhiregistry.model.Member;

public class BirthdayCalendar {
	
	
	private Member member;
	private String day;
	private String month;
	private String year;
	private DateTime dateTime;
	private int age;
	private int dayOfMonth;
	private String compositeDay;
	public Integer dueTime;
	private Integer memberId;
	
	
	
	
	
	public BirthdayCalendar(Member member, String day, String month,
			String year, DateTime dateTime, int age, Integer dueTime) {
		super();
		this.member = member;
		this.memberId = member.getMemberId();
		this.day = day;
		this.month = month;
		this.year = year;
		this.dateTime = dateTime;
		this.age = age;
		this.dueTime = dueTime;
		this.compositeDay = new String(dateTime.getDayOfMonth()+" "+month);
	}

	public BirthdayCalendar(){
		
	}
	
	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}


	public String getDay() {
		return day;
	}


	public void setDay(String day) {
		this.day = day;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public DateTime getDateTime() {
		return dateTime;
	}


	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}

	public Integer getDueTime() {
		return dueTime;
	}

	public void setDueTime(Integer dueTime) {
		this.dueTime = dueTime;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public String getCompositeDay() {
		return compositeDay;
	}

	public void setCompositeDay(String compositeDay) {
		this.compositeDay = compositeDay;
	}
	
	
	
	
	

}

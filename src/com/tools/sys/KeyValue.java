package com.tools.sys;

public class KeyValue
{
	private Object key;
	private Object value;
	private Object otval;
	private Object type;
	
	public KeyValue(String key)
	{
		super();
		this.key = key;
		this.value = key;
		this.otval = key;
	}
	public KeyValue(String key,String value)
	{
		super();
		this.key = key;
		this.value = value;
	}
	public KeyValue(Long key, String value)
	{
		super();
		this.key = key;
		this.value = value;
	}
	public KeyValue(Integer key, String value)
	{
		super();
		this.key = key;
		this.value = value;
	}
	public KeyValue(String key, boolean value)
	{
		super();
		this.key = key;
		this.value = value;
	}
	public KeyValue(String key,Double value)
	{
		super();
		this.key = key;
		this.value = value;
	}
	public KeyValue(String key,String value, Boolean istrue)
	{
		super();
		this.key = key;
		this.value = value;
		this.otval = istrue;
	}
	
	public KeyValue(String key,String value, String otval)
	{
		super();
		this.key = key;
		this.value = value;
		this.otval = otval;
	}
	
	public KeyValue(String key,String value, String otval,String type)
	{
		super();
		this.key = key;
		this.value = value;
		this.otval = otval;
		this.type=type;
	}
	
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Object getOtval() {
		return otval;
	}
	public void setOtval(Object otval) {
		this.otval = otval;
	}
	public Object getType() {
		return type;
	}
	public void setType(Object type) {
		this.type = type;
	}	
	
}

package com.yuzhi.fine.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_student")
public class Student extends BaseDaoEnabled<Student, Integer>
{
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField
	private String name;
	
	public Student()
	{
	}

	

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}

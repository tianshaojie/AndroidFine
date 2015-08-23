package com.yuzhi.fine.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = "tb_user")
public class User 
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(columnName = "name")
	private String name;

	@ForeignCollectionField
	private Collection<Article> articles;

	public Collection<Article> getArticles()
	{
		return articles;
	}

	public void setArticles(Collection<Article> articles)
	{
		this.articles = articles;
	}

	public User()
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

	@Override
	public String toString()
	{
		return "User [id=" + id + ", name=" + name + ", articles=" + articles
				+ "]";
	}

	


	
}

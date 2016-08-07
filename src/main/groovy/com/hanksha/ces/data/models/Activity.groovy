package com.hanksha.ces.data.models

import groovy.transform.Canonical

import javax.validation.constraints.Size;

import javax.validation.constraints.NotNull;

@Canonical
public class Activity {

	@NotNull
	int id;

	@NotNull
	@Size(min = 1, max = 45)
	String title;

	@NotNull
	Date dateStart;

	@NotNull
	Date dateEnd;

	String desc;

}

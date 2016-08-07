package com.hanksha.ces.data.models

import groovy.transform.Canonical;

import javax.validation.constraints.NotNull;

@Canonical
public class Participation {

	int id;

	@NotNull
	int memberId;

	@NotNull
	int activityId;

	@NotNull
	Date date;

	String remarks;

}
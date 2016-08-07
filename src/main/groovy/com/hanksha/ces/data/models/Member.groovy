package com.hanksha.ces.data.models

import groovy.transform.Canonical

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Canonical
class Member {

    int id;

    @NotNull
    @Size(min = 1, max = 45)
    String firstName

    @NotNull
    @Size(min = 1, max = 45)
    String lastName

    @NotNull
    @Size(min = 1, max = 45)
    String dptId

}
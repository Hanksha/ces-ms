package com.hanksha.ces.data.models

import groovy.transform.Canonical

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * Created by vivien on 7/10/16.
 */
@Canonical
class Department {

    @NotNull
    @Size(min = 1, max = 10)
    String id

    @NotNull
    @Size(min = 1, max = 100)
    String name

}

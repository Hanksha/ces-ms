package com.hanksha.ces.data.models

import groovy.transform.Canonical

import javax.validation.constraints.NotNull

/**
 * Created by vivien on 7/13/16.
 */

@Canonical
class Involvement {

    @NotNull
    int participationId

    @NotNull
    String type

}

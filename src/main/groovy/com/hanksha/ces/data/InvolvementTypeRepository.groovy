package com.hanksha.ces.data

import com.hanksha.ces.data.models.InvolvementType

/**
 * Created by vivien on 7/10/16.
 */

interface InvolvementTypeRepository {

    InvolvementType findOne(String name)

    List<InvolvementType> findAll()

    void delete(String name)

    void save(InvolvementType involvementType)

    void update(InvolvementType involvementType)

}
package com.hanksha.ces

import com.hanksha.ces.data.models.Activity
import com.hanksha.ces.data.models.Department
import com.hanksha.ces.data.models.InvolvementType
import com.hanksha.ces.data.models.Member
import com.hanksha.ces.data.models.Participation
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*

@RunWith(SpringJUnit4ClassRunner)
@FixMethodOrder(MethodSorters.JVM)
@ActiveProfiles(['test'])
@SpringApplicationConfiguration(classes = CesMsApplication)
@WebAppConfiguration
class CesMsApplicationTests {

	@Autowired
	WebApplicationContext webApplicationContext

	MockMvc mockMvc

	JsonSlurper slurper

	def final MEMBER_URI = '/api/members'

	def final DEPARTMENT_URI = '/api/departments'

	def final INVOLVEMENT_TYPE_URI = '/api/involvement-types'

	def final ACTIVITY_URI = '/api/activities'

	def final PARTICIPATION_URI = '/api/participations'

	@Before
	void setup() {
		mockMvc = new MockMvcBuilders().webAppContextSetup(webApplicationContext)
				.build()

		slurper = new JsonSlurper()
	}

	/* Member API tests */

	@Test
	void 'member-get'() {
		 mockMvc.perform(get("$MEMBER_URI/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$.id', is(1)))
			.andExpect(jsonPath('$.firstName', is('SuperAdmin')))
			.andExpect(jsonPath('$.lastName', is('SuperAdmin')))
			.andExpect(jsonPath('$.dptId', is('CBA')))
	}

	@Test
	void 'member-list'() {
		mockMvc.perform(get(MEMBER_URI))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$', hasSize(9)))
			.andExpect(jsonPath('$[0].id', is(1)))
			.andExpect(jsonPath('$[0].firstName', is('SuperAdmin')))
			.andExpect(jsonPath('$[0].lastName', is('SuperAdmin')))
			.andExpect(jsonPath('$[0].dptId', is('CBA')))
			.andExpect(jsonPath('$[1].id', is(2)))
			.andExpect(jsonPath('$[1].firstName', is('John')))
			.andExpect(jsonPath('$[1].lastName', is('Doe')))
			.andExpect(jsonPath('$[1].dptId', is('CEA')))
	}

	@Test
	void 'member-create'() {
		def member = new Member(firstName: 'test', lastName: 'test', dptId: 'CICT')

		mockMvc.perform(post(MEMBER_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(member).bytes))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.id', is(10)))
				.andExpect(jsonPath('$.firstName', is(member.firstName)))
				.andExpect(jsonPath('$.lastName', is(member.lastName)))
				.andExpect(jsonPath('$.dptId', is(member.dptId)))

	}

	@Test
	void 'member-update'() {
		def member = new Member(firstName: 'test', lastName: 'test', dptId: 'CICT')

		mockMvc.perform(post(MEMBER_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(member).bytes))
				.andExpect(status().isOk())
				.andDo({member.id = slurper.parseText(it.response.contentAsString).id})

		member.setDptId('CBA')

		mockMvc.perform(put("$MEMBER_URI/$member.id").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JsonOutput.toJson(member).bytes))
				.andExpect(status().isOk())

		mockMvc.perform(get("$MEMBER_URI/$member.id"))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.dptId', is(member.dptId)))
	}

	@Test
	void 'member-delete'() {
		def member = new Member(firstName: 'test', lastName: 'test', dptId: 'CICT')

		mockMvc.perform(post(MEMBER_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(member)))
				.andDo({member.id = slurper.parseText(it.response.contentAsString).id})


		mockMvc.perform(delete("$MEMBER_URI/$member.id"))
				.andExpect(status().isOk())

		mockMvc.perform(get("$MEMBER_URI/$member.id"))
				.andExpect(status().isNotFound())
	}

	/* Department API tests */

	@Test
	void 'department-get'() {
		mockMvc.perform(get("$DEPARTMENT_URI/CICT"))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$.id', is('CICT')))
			.andExpect(jsonPath('$.name', is('College of Information and Communications Technology')))
	}

	@Test
	void 'department-list'() {
		mockMvc.perform(get(DEPARTMENT_URI))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$', hasSize(9)))
			.andExpect(jsonPath('$[0].id', is('CASEd')))
			.andExpect(jsonPath('$[0].name', is('College of Arts, Science and Eduction')))
	}

	@Test
	void 'department-create'() {
		def dpt = new Department(id: 'TST', name: 'Test')

		mockMvc.perform(post(DEPARTMENT_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(dpt).bytes))
				.andExpect(status().isOk())

		mockMvc.perform(get("$DEPARTMENT_URI/$dpt.id"))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$.id', is(dpt.id)))
			.andExpect(jsonPath('$.name', is(dpt.name)))
	}

	@Test
	void 'department-update'() {
		def dpt = new Department(id: 'UDPT', name: 'To be updated')

		mockMvc.perform(post(DEPARTMENT_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JsonOutput.toJson(dpt).bytes))
				.andExpect(status().isOk())

		dpt.name = 'Updated'

		mockMvc.perform(put("$DEPARTMENT_URI/$dpt.id").contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(dpt).bytes))

		mockMvc.perform(get("$DEPARTMENT_URI/$dpt.id"))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.name', is(dpt.name)))
	}

	@Test
	void 'department-delete'() {
		def dpt = new Department(id: 'DEL', name: 'To be deleted')

		mockMvc.perform(post(DEPARTMENT_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(dpt).bytes))
				.andExpect(status().isOk())

		mockMvc.perform(delete("$DEPARTMENT_URI/$dpt.id"))
			.andExpect(status().isOk())

		mockMvc.perform(get("$DEPARTMENT_URI/$dpt.id"))
			.andExpect(status().isNotFound())
	}

	/* Involvement types tests */

	@Test
	void 'involvement-type-get'() {
		mockMvc.perform(get("$INVOLVEMENT_TYPE_URI/Participant"))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$.name', is('Participant')))
			.andExpect(jsonPath('$.desc', is('Lorem ipsum dolor sit amet, consectetur adipiscing elit.')))
	}

	@Test
	void 'involvement-type-list'() {
		mockMvc.perform(get(INVOLVEMENT_TYPE_URI))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$', hasSize(8)))
			.andExpect(jsonPath('$[0].name', is('Coordinator')))
			.andExpect(jsonPath('$[0].desc', is('Lorem ipsum dolor sit amet, consectetur adipiscing elit.')))
	}

	@Test
	void 'involvement-type-create'() {

		def involType = new InvolvementType(name: 'Test', desc: 'This is a test')

		mockMvc.perform(post(INVOLVEMENT_TYPE_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(involType).bytes))
				.andExpect(status().isOk())

		mockMvc.perform(get("$INVOLVEMENT_TYPE_URI/$involType.name"))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.name', is(involType.name)))
				.andExpect(jsonPath('$.desc', is(involType.desc)))
	}

	@Test
	void 'involvement-type-update'() {
		def involType = new InvolvementType(name: 'Update', desc: 'To be updated')

		mockMvc.perform(post(INVOLVEMENT_TYPE_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(involType).bytes))
				.andExpect(status().isOk())

		involType.desc = 'Updated'

		mockMvc.perform(put("$INVOLVEMENT_TYPE_URI/$involType.name").contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(involType).bytes))
				.andExpect(status().isOk())

		mockMvc.perform(get("$INVOLVEMENT_TYPE_URI/$involType.name"))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.name', is(involType.name)))
				.andExpect(jsonPath('$.desc', is(involType.desc)))
	}

	@Test
	void 'involvement-type-delete'() {
		def involType = new InvolvementType(name: 'Delete', desc: 'To be deleted')

		mockMvc.perform(post(INVOLVEMENT_TYPE_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JsonOutput.toJson(involType).bytes))
				.andExpect(status().isOk())

		mockMvc.perform(delete("$INVOLVEMENT_TYPE_URI/$involType.name"))
			.andExpect(status().isOk())

		mockMvc.perform(get("$INVOLVEMENT_TYPE_URI/$involType.name"))
				.andExpect(status().isNotFound())
	}

	/* Activity API tests */

	@Test
	void 'activity-get'() {
		mockMvc.perform(get("$ACTIVITY_URI/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$.id', is(1)))
			.andExpect(jsonPath('$.title', is('Activity 1')))
	}

	@Test
	void 'activity-list'() {
		mockMvc.perform(get(ACTIVITY_URI))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$', hasSize(16)))
				.andExpect(jsonPath('$[0].title', is('Activity 1')))

		mockMvc.perform(get("$ACTIVITY_URI").param('q', 'title:Activity 1'))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$', hasSize(1)))
				.andExpect(jsonPath('$[0].title', is('Activity 1')))

		mockMvc.perform(get("$ACTIVITY_URI").param('q', "date:${new Date().format('yyyy-MM-dd')}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$', hasSize(16)))
	}

	@Test
	void 'activity-create'() {

		def activity = new Activity(
				title: 'Test Activity',
				dateStart: new Date() + 1,
				dateEnd: new Date() + 2,
				desc: 'A test activity')

		mockMvc.perform(post(ACTIVITY_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(activity).bytes))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.id', notNullValue()))
				.andExpect(jsonPath('$.title', is(activity.title)))
	}

	@Test
	void 'activity-update'() {
		def activity = new Activity(
				title: 'Update Activity',
				dateStart: new Date() + 1,
				dateEnd: new Date() + 2,
				desc: 'To be updated')

		mockMvc.perform(post(ACTIVITY_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(activity).bytes))
				.andExpect(status().isOk())
				.andDo({activity.id = slurper.parseText(it.response.contentAsString).id})

		activity.title = 'Updated'

		mockMvc.perform(put("$ACTIVITY_URI/$activity.id").contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(activity)))
				.andExpect(status().isOk())

		mockMvc.perform(get("$ACTIVITY_URI/$activity.id"))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$.title', is(activity.title)))
	}

	@Test
	void 'activity-delete'() {
		def activity = new Activity(
				title: 'Activity to be deleted',
				dateStart: new Date() + 1,
				dateEnd: new Date() + 2,
				desc: 'An activity')

		mockMvc.perform(post(ACTIVITY_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(activity).bytes))
				.andDo({activity.id = slurper.parseText(it.response.contentAsString).id})

		mockMvc.perform(delete("$ACTIVITY_URI/$activity.id"))
			.andExpect(status().isOk())

		mockMvc.perform(get("$ACTIVITY_URI/$activity.id"))
				.andExpect(status().isNotFound())
	}

	/* Participations API tests */

	@Test
	void 'participation-get'() {
		mockMvc.perform(get("$PARTICIPATION_URI/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$.id', is(1)))
			.andExpect(jsonPath('$.memberId', is(1)))
			.andExpect(jsonPath('$.activityId', is(1)))
			.andExpect(jsonPath('$.remarks', is('Great')))
	}

	@Test
	void 'participation-list'() {
		mockMvc.perform(get(PARTICIPATION_URI))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$', hasSize(5)))
			.andExpect(jsonPath('$[0].id', is(1)))
			.andExpect(jsonPath('$[0].memberId', is(1)))
			.andExpect(jsonPath('$[0].remarks', is('Great')))
	}

	@Test
	void 'participation-list-member'() {
		mockMvc.perform(get("$PARTICIPATION_URI/member/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$', hasSize(5)))
			.andExpect(jsonPath('$[0].id', is(1)))
			.andExpect(jsonPath('$[0].memberId', is(1)))
			.andExpect(jsonPath('$[0].remarks', is('Great')))
	}

	@Test
	void 'participation-create'() {
		def pcpt = new Participation(memberId: 2, activityId: 1, date: new Date(), remarks: 'Great')

		mockMvc.perform(post(PARTICIPATION_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(pcpt).bytes))
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.id', notNullValue()))
				.andExpect(jsonPath('$.memberId', is(pcpt.memberId)))
				.andExpect(jsonPath('$.activityId', is(pcpt.activityId)))
				.andExpect(jsonPath('$.remarks', is(pcpt.remarks)))
	}

	@Test
	void 'participation-update'() {
		def pcpt = new Participation(memberId: 2, activityId: 2, date: new Date(), remarks: 'Great')

		mockMvc.perform(post(PARTICIPATION_URI).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JsonOutput.toJson(pcpt).bytes))
				.andExpect(status().isOk())
				.andDo({pcpt.id = slurper.parseText(it.response.contentAsString).id})

		pcpt.remarks = 'Updated'

		mockMvc.perform(put("$PARTICIPATION_URI/$pcpt.id").contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(JsonOutput.toJson(pcpt).bytes))
				.andExpect(status().isOk())

		mockMvc.perform(get("$PARTICIPATION_URI/$pcpt.id"))
			.andExpect(status().isOk())
			.andExpect(jsonPath('$.remarks', is(pcpt.remarks)))
	}

}

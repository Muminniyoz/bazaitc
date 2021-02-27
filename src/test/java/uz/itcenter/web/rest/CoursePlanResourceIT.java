package uz.itcenter.web.rest;

import uz.itcenter.BazaitcApp;
import uz.itcenter.domain.CoursePlan;
import uz.itcenter.repository.CoursePlanRepository;
import uz.itcenter.service.CoursePlanService;
import uz.itcenter.service.dto.CoursePlanDTO;
import uz.itcenter.service.mapper.CoursePlanMapper;
import uz.itcenter.service.dto.CoursePlanCriteria;
import uz.itcenter.service.CoursePlanQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uz.itcenter.domain.enumeration.Month;
/**
 * Integration tests for the {@link CoursePlanResource} REST controller.
 */
@SpringBootTest(classes = BazaitcApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CoursePlanResourceIT {

    private static final Month DEFAULT_MONTH = Month.JANUARY;
    private static final Month UPDATED_MONTH = Month.FEBRUARY;

    private static final String DEFAULT_TECHNOLOGY = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGY = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXTRA_PRICE = 1;
    private static final Integer UPDATED_EXTRA_PRICE = 2;
    private static final Integer SMALLER_EXTRA_PRICE = 1 - 1;

    @Autowired
    private CoursePlanRepository coursePlanRepository;

    @Autowired
    private CoursePlanMapper coursePlanMapper;

    @Autowired
    private CoursePlanService coursePlanService;

    @Autowired
    private CoursePlanQueryService coursePlanQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoursePlanMockMvc;

    private CoursePlan coursePlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoursePlan createEntity(EntityManager em) {
        CoursePlan coursePlan = new CoursePlan()
            .month(DEFAULT_MONTH)
            .technology(DEFAULT_TECHNOLOGY)
            .extraPrice(DEFAULT_EXTRA_PRICE);
        return coursePlan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoursePlan createUpdatedEntity(EntityManager em) {
        CoursePlan coursePlan = new CoursePlan()
            .month(UPDATED_MONTH)
            .technology(UPDATED_TECHNOLOGY)
            .extraPrice(UPDATED_EXTRA_PRICE);
        return coursePlan;
    }

    @BeforeEach
    public void initTest() {
        coursePlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoursePlan() throws Exception {
        int databaseSizeBeforeCreate = coursePlanRepository.findAll().size();
        // Create the CoursePlan
        CoursePlanDTO coursePlanDTO = coursePlanMapper.toDto(coursePlan);
        restCoursePlanMockMvc.perform(post("/api/course-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursePlanDTO)))
            .andExpect(status().isCreated());

        // Validate the CoursePlan in the database
        List<CoursePlan> coursePlanList = coursePlanRepository.findAll();
        assertThat(coursePlanList).hasSize(databaseSizeBeforeCreate + 1);
        CoursePlan testCoursePlan = coursePlanList.get(coursePlanList.size() - 1);
        assertThat(testCoursePlan.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testCoursePlan.getTechnology()).isEqualTo(DEFAULT_TECHNOLOGY);
        assertThat(testCoursePlan.getExtraPrice()).isEqualTo(DEFAULT_EXTRA_PRICE);
    }

    @Test
    @Transactional
    public void createCoursePlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coursePlanRepository.findAll().size();

        // Create the CoursePlan with an existing ID
        coursePlan.setId(1L);
        CoursePlanDTO coursePlanDTO = coursePlanMapper.toDto(coursePlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursePlanMockMvc.perform(post("/api/course-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursePlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoursePlan in the database
        List<CoursePlan> coursePlanList = coursePlanRepository.findAll();
        assertThat(coursePlanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCoursePlans() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList
        restCoursePlanMockMvc.perform(get("/api/course-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coursePlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY)))
            .andExpect(jsonPath("$.[*].extraPrice").value(hasItem(DEFAULT_EXTRA_PRICE)));
    }
    
    @Test
    @Transactional
    public void getCoursePlan() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get the coursePlan
        restCoursePlanMockMvc.perform(get("/api/course-plans/{id}", coursePlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coursePlan.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.technology").value(DEFAULT_TECHNOLOGY))
            .andExpect(jsonPath("$.extraPrice").value(DEFAULT_EXTRA_PRICE));
    }


    @Test
    @Transactional
    public void getCoursePlansByIdFiltering() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        Long id = coursePlan.getId();

        defaultCoursePlanShouldBeFound("id.equals=" + id);
        defaultCoursePlanShouldNotBeFound("id.notEquals=" + id);

        defaultCoursePlanShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCoursePlanShouldNotBeFound("id.greaterThan=" + id);

        defaultCoursePlanShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCoursePlanShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCoursePlansByMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where month equals to DEFAULT_MONTH
        defaultCoursePlanShouldBeFound("month.equals=" + DEFAULT_MONTH);

        // Get all the coursePlanList where month equals to UPDATED_MONTH
        defaultCoursePlanShouldNotBeFound("month.equals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where month not equals to DEFAULT_MONTH
        defaultCoursePlanShouldNotBeFound("month.notEquals=" + DEFAULT_MONTH);

        // Get all the coursePlanList where month not equals to UPDATED_MONTH
        defaultCoursePlanShouldBeFound("month.notEquals=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByMonthIsInShouldWork() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where month in DEFAULT_MONTH or UPDATED_MONTH
        defaultCoursePlanShouldBeFound("month.in=" + DEFAULT_MONTH + "," + UPDATED_MONTH);

        // Get all the coursePlanList where month equals to UPDATED_MONTH
        defaultCoursePlanShouldNotBeFound("month.in=" + UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where month is not null
        defaultCoursePlanShouldBeFound("month.specified=true");

        // Get all the coursePlanList where month is null
        defaultCoursePlanShouldNotBeFound("month.specified=false");
    }

    @Test
    @Transactional
    public void getAllCoursePlansByTechnologyIsEqualToSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where technology equals to DEFAULT_TECHNOLOGY
        defaultCoursePlanShouldBeFound("technology.equals=" + DEFAULT_TECHNOLOGY);

        // Get all the coursePlanList where technology equals to UPDATED_TECHNOLOGY
        defaultCoursePlanShouldNotBeFound("technology.equals=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByTechnologyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where technology not equals to DEFAULT_TECHNOLOGY
        defaultCoursePlanShouldNotBeFound("technology.notEquals=" + DEFAULT_TECHNOLOGY);

        // Get all the coursePlanList where technology not equals to UPDATED_TECHNOLOGY
        defaultCoursePlanShouldBeFound("technology.notEquals=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByTechnologyIsInShouldWork() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where technology in DEFAULT_TECHNOLOGY or UPDATED_TECHNOLOGY
        defaultCoursePlanShouldBeFound("technology.in=" + DEFAULT_TECHNOLOGY + "," + UPDATED_TECHNOLOGY);

        // Get all the coursePlanList where technology equals to UPDATED_TECHNOLOGY
        defaultCoursePlanShouldNotBeFound("technology.in=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByTechnologyIsNullOrNotNull() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where technology is not null
        defaultCoursePlanShouldBeFound("technology.specified=true");

        // Get all the coursePlanList where technology is null
        defaultCoursePlanShouldNotBeFound("technology.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoursePlansByTechnologyContainsSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where technology contains DEFAULT_TECHNOLOGY
        defaultCoursePlanShouldBeFound("technology.contains=" + DEFAULT_TECHNOLOGY);

        // Get all the coursePlanList where technology contains UPDATED_TECHNOLOGY
        defaultCoursePlanShouldNotBeFound("technology.contains=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByTechnologyNotContainsSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where technology does not contain DEFAULT_TECHNOLOGY
        defaultCoursePlanShouldNotBeFound("technology.doesNotContain=" + DEFAULT_TECHNOLOGY);

        // Get all the coursePlanList where technology does not contain UPDATED_TECHNOLOGY
        defaultCoursePlanShouldBeFound("technology.doesNotContain=" + UPDATED_TECHNOLOGY);
    }


    @Test
    @Transactional
    public void getAllCoursePlansByExtraPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where extraPrice equals to DEFAULT_EXTRA_PRICE
        defaultCoursePlanShouldBeFound("extraPrice.equals=" + DEFAULT_EXTRA_PRICE);

        // Get all the coursePlanList where extraPrice equals to UPDATED_EXTRA_PRICE
        defaultCoursePlanShouldNotBeFound("extraPrice.equals=" + UPDATED_EXTRA_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByExtraPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where extraPrice not equals to DEFAULT_EXTRA_PRICE
        defaultCoursePlanShouldNotBeFound("extraPrice.notEquals=" + DEFAULT_EXTRA_PRICE);

        // Get all the coursePlanList where extraPrice not equals to UPDATED_EXTRA_PRICE
        defaultCoursePlanShouldBeFound("extraPrice.notEquals=" + UPDATED_EXTRA_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByExtraPriceIsInShouldWork() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where extraPrice in DEFAULT_EXTRA_PRICE or UPDATED_EXTRA_PRICE
        defaultCoursePlanShouldBeFound("extraPrice.in=" + DEFAULT_EXTRA_PRICE + "," + UPDATED_EXTRA_PRICE);

        // Get all the coursePlanList where extraPrice equals to UPDATED_EXTRA_PRICE
        defaultCoursePlanShouldNotBeFound("extraPrice.in=" + UPDATED_EXTRA_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByExtraPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where extraPrice is not null
        defaultCoursePlanShouldBeFound("extraPrice.specified=true");

        // Get all the coursePlanList where extraPrice is null
        defaultCoursePlanShouldNotBeFound("extraPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCoursePlansByExtraPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where extraPrice is greater than or equal to DEFAULT_EXTRA_PRICE
        defaultCoursePlanShouldBeFound("extraPrice.greaterThanOrEqual=" + DEFAULT_EXTRA_PRICE);

        // Get all the coursePlanList where extraPrice is greater than or equal to UPDATED_EXTRA_PRICE
        defaultCoursePlanShouldNotBeFound("extraPrice.greaterThanOrEqual=" + UPDATED_EXTRA_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByExtraPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where extraPrice is less than or equal to DEFAULT_EXTRA_PRICE
        defaultCoursePlanShouldBeFound("extraPrice.lessThanOrEqual=" + DEFAULT_EXTRA_PRICE);

        // Get all the coursePlanList where extraPrice is less than or equal to SMALLER_EXTRA_PRICE
        defaultCoursePlanShouldNotBeFound("extraPrice.lessThanOrEqual=" + SMALLER_EXTRA_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByExtraPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where extraPrice is less than DEFAULT_EXTRA_PRICE
        defaultCoursePlanShouldNotBeFound("extraPrice.lessThan=" + DEFAULT_EXTRA_PRICE);

        // Get all the coursePlanList where extraPrice is less than UPDATED_EXTRA_PRICE
        defaultCoursePlanShouldBeFound("extraPrice.lessThan=" + UPDATED_EXTRA_PRICE);
    }

    @Test
    @Transactional
    public void getAllCoursePlansByExtraPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        // Get all the coursePlanList where extraPrice is greater than DEFAULT_EXTRA_PRICE
        defaultCoursePlanShouldNotBeFound("extraPrice.greaterThan=" + DEFAULT_EXTRA_PRICE);

        // Get all the coursePlanList where extraPrice is greater than SMALLER_EXTRA_PRICE
        defaultCoursePlanShouldBeFound("extraPrice.greaterThan=" + SMALLER_EXTRA_PRICE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCoursePlanShouldBeFound(String filter) throws Exception {
        restCoursePlanMockMvc.perform(get("/api/course-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coursePlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY)))
            .andExpect(jsonPath("$.[*].extraPrice").value(hasItem(DEFAULT_EXTRA_PRICE)));

        // Check, that the count call also returns 1
        restCoursePlanMockMvc.perform(get("/api/course-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCoursePlanShouldNotBeFound(String filter) throws Exception {
        restCoursePlanMockMvc.perform(get("/api/course-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCoursePlanMockMvc.perform(get("/api/course-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCoursePlan() throws Exception {
        // Get the coursePlan
        restCoursePlanMockMvc.perform(get("/api/course-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoursePlan() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        int databaseSizeBeforeUpdate = coursePlanRepository.findAll().size();

        // Update the coursePlan
        CoursePlan updatedCoursePlan = coursePlanRepository.findById(coursePlan.getId()).get();
        // Disconnect from session so that the updates on updatedCoursePlan are not directly saved in db
        em.detach(updatedCoursePlan);
        updatedCoursePlan
            .month(UPDATED_MONTH)
            .technology(UPDATED_TECHNOLOGY)
            .extraPrice(UPDATED_EXTRA_PRICE);
        CoursePlanDTO coursePlanDTO = coursePlanMapper.toDto(updatedCoursePlan);

        restCoursePlanMockMvc.perform(put("/api/course-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursePlanDTO)))
            .andExpect(status().isOk());

        // Validate the CoursePlan in the database
        List<CoursePlan> coursePlanList = coursePlanRepository.findAll();
        assertThat(coursePlanList).hasSize(databaseSizeBeforeUpdate);
        CoursePlan testCoursePlan = coursePlanList.get(coursePlanList.size() - 1);
        assertThat(testCoursePlan.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testCoursePlan.getTechnology()).isEqualTo(UPDATED_TECHNOLOGY);
        assertThat(testCoursePlan.getExtraPrice()).isEqualTo(UPDATED_EXTRA_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCoursePlan() throws Exception {
        int databaseSizeBeforeUpdate = coursePlanRepository.findAll().size();

        // Create the CoursePlan
        CoursePlanDTO coursePlanDTO = coursePlanMapper.toDto(coursePlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoursePlanMockMvc.perform(put("/api/course-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursePlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoursePlan in the database
        List<CoursePlan> coursePlanList = coursePlanRepository.findAll();
        assertThat(coursePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoursePlan() throws Exception {
        // Initialize the database
        coursePlanRepository.saveAndFlush(coursePlan);

        int databaseSizeBeforeDelete = coursePlanRepository.findAll().size();

        // Delete the coursePlan
        restCoursePlanMockMvc.perform(delete("/api/course-plans/{id}", coursePlan.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CoursePlan> coursePlanList = coursePlanRepository.findAll();
        assertThat(coursePlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

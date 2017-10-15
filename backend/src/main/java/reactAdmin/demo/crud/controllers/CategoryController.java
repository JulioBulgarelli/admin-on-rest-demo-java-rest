package reactAdmin.demo.crud.controllers;


import reactAdmin.demo.crud.entities.Category;
import reactAdmin.demo.crud.repos.AORSpecifications;
import reactAdmin.demo.crud.repos.CategoryRepository;
import reactAdmin.demo.crud.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class CategoryController {
    @Autowired
    private CategoryRepository repo;

    @Autowired
    private AORSpecifications<Category> specification;

    @Autowired
    private ApiUtils utils;

    @RequestMapping(value = "categories", method = RequestMethod.POST)
    public Category create(@RequestBody Category category) {
        return repo.save(category);
    }

    @RequestMapping(value = "categories/{id}", method = RequestMethod.PUT)
    public Category update(@RequestBody Category category, @PathVariable int id) {
        category.id = id;
        return repo.save(category);
    }

    @RequestMapping(value = "categories/{id}/published/{value}", method = RequestMethod.POST)
    public Category publishedUpdate(@PathVariable int id, @PathVariable boolean value) {
        Category category = repo.findOne(id);
        category.published = value;
        return repo.save(category);
    }


    @RequestMapping(value = "categories/{id}", method = RequestMethod.GET)
    public Category getById(@PathVariable int id) {
        return repo.findOne(id);
    }

    @RequestMapping(value = "categories", method = RequestMethod.GET)
    public Iterable<Category> filterBy(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr, @RequestParam(required = false, name="sort") String sortStr) {
        return utils.filterByHelper(repo, specification, filterStr, rangeStr, sortStr);
    }
}
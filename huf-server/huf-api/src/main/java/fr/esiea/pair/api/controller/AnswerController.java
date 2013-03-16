package fr.esiea.pair.api.controller;

import fr.esiea.pair.model.Answer;
import fr.esiea.pair.model.Question;
import fr.esiea.pair.model.model;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/answers")
public class AnswerController extends ApiExceptionHandlerController {

    public AnswerController() {
        // TODO
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Iterable<Answer> list() {
        // TODO
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Answer getById(@PathVariable("id") String answerId) {
        // TODO
        return null;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void create(@RequestBody model user) {
        // TODO
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void edit(@RequestBody model user) {
        // TODO
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable ObjectId id) {
        // TODO
    }

    @RequestMapping(value = "/{id}/author", method = RequestMethod.GET)
    public model getAuthor(@PathVariable ObjectId id) {
        // TODO
        return null;
    }

    @RequestMapping(value = "/{id}/question", method = RequestMethod.GET)
    public Question getQuestion(@PathVariable ObjectId id) {
        // TODO
        return null;
    }

}

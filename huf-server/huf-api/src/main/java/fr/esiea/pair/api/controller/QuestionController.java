package fr.esiea.pair.api.controller;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.esiea.pair.dao.exception.NoDataBaseConnectionException;
import fr.esiea.pair.model.Question;
import fr.esiea.pair.model.User;
import fr.esiea.pair.service.exception.InvalidObjectIdException;
import fr.esiea.pair.service.exception.WrongFormatException.WrongQuestionFormatException;
import fr.esiea.pair.service.manager.QuestionManager;

@Controller
@RequestMapping("/questions")
public class QuestionController extends ApiExceptionHandlerController {

    private QuestionManager questionManager;

    public QuestionController() throws NoDataBaseConnectionException {

        questionManager = new QuestionManager();
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Iterable<Question> list() throws NoDataBaseConnectionException {

        return questionManager.list();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Question getById(@PathVariable("id") String questionId) throws NoDataBaseConnectionException, InvalidObjectIdException {

        return questionManager.get(questionId);

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Question question) throws NoDataBaseConnectionException, WrongQuestionFormatException {
        //Test
        User fake = new User("Toto", "");

        question.setAuthorId(fake.getId());
        question.setAuthorPseudo(fake.getPseudo());
        //end of test

        questionManager.create(question);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void edit(@RequestBody Question question, @PathVariable ObjectId id) throws NoDataBaseConnectionException, WrongQuestionFormatException {

        questionManager.update(question);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable ObjectId id) throws NoDataBaseConnectionException, InvalidObjectIdException {

        questionManager.delete(id);

    }

    @RequestMapping(value = "/{id}/author", method = RequestMethod.GET)
    public void getAuthor(@PathVariable ObjectId id) {
        // TODO
    }

    @RequestMapping(value = "/{id}/answers", method = RequestMethod.GET)
    public void getAnswers(@PathVariable ObjectId id) {
        // TODO
    }

    @RequestMapping(value = "/{id}/title", method = RequestMethod.GET)
    public void getTitle(@PathVariable ObjectId id) {
        // TODO
    }
}

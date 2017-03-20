import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";
    ChangeMachine myChangeMachine = new ChangeMachine();

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/form.vtl");

      // model.put("myChangeMachine", myChangeMachine);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/change_output", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Float amount = Float.parseFloat(request.queryParams("amount"));
      // ChangeMachine myChangeMachine = model.get(myChangeMachine);
      String myChangeString = myChangeMachine.makeChange(amount);
      int remainingQuarters = myChangeMachine.getQuarters();
      int remainingDimes = myChangeMachine.getDimes();
      int remainingNickels = myChangeMachine.getNickels();
      int remainingPennies = myChangeMachine.getPennies();
      model.put("changeString", myChangeString);
      model.put("amount", amount);
      model.put("remainingQuarters", remainingQuarters);
      model.put("remainingDimes", remainingDimes);
      model.put("remainingNickels", remainingNickels);
      model.put("remainingPennies", remainingPennies);
      model.put("template", "templates/change_output.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}

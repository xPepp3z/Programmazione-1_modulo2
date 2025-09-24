package Controller;

import Service.OperationCalculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/calcolatrice")
public class OperationEndPoint {


    private final OperationCalculator operationCalculator; //come se creasse l'oggetto

    public OperationEndPoint (OperationCalculator operationCalculator) {
        this.operationCalculator = operationCalculator;
    }
    @GetMapping(path = "/addizione/{n1}/{n2}")
    public int addizione(@PathVariable int n1, @PathVariable int n2) {
        return operationCalculator.addizione(n1, n2);
    }

    @GetMapping(path = "/sottrazione/{n1}/{n2}")
    public int sottrazione(@PathVariable int n1, @PathVariable int n2) {
        return operationCalculator.sottrazione(n1, n2);
    }

    @GetMapping(path = "/moltiplicazione/{n1}/{n2}")
    public int moltiplicazione(@PathVariable int n1, @PathVariable int n2) {
        return operationCalculator.moltiplicazione(n1, n2);
    }


    @GetMapping(path = "/divisione/{n1}/{n2}")
    public float divisione(@PathVariable int n1, @PathVariable int n2) {
        return operationCalculator.divisione(n1, n2);
    }

}








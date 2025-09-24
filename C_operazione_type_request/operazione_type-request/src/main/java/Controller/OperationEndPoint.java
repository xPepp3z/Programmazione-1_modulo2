package Controller;

import Services.OperationCalculator;
import dto.OperationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calcolatrice")
public class OperationEndPoint {
    private final OperationCalculator operationCalculator; //come se creasse l'oggetto

    public OperationEndPoint (OperationCalculator operationCalculator) {
        this.operationCalculator = operationCalculator;

    }
    @GetMapping(path = "/addizione/{n1}/{n2}")   //path variable
    public int addizione(@PathVariable int n1, @PathVariable int n2) {
        return operationCalculator.addizione(n1, n2);
    }

    @GetMapping(path = "/sottrazione")      //Query Params
    public int sottrazione(@RequestParam int n1, @RequestParam int n2) {
        return operationCalculator.sottrazione(n1, n2);
    }

    @PostMapping (path = "/moltiplicazione")    //JSON
    public int moltiplicazione(@RequestBody OperationRequest request ) {
        return operationCalculator.moltiplicazione(request.getN1(), request.getN2());
    }

    @GetMapping(path = "/divisione/{n1}/{n2}")      //path variable
    public float divisione(@PathVariable int n1, @PathVariable int n2) {
        return operationCalculator.divisione(n1, n2);
    }
}

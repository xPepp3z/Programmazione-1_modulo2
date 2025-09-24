package Services;

import org.springframework.stereotype.Service;

    @Service
    public class OperationCalculator {
        public int addizione(int n1, int n2) {
            return n1 + n2;
        }

        public int sottrazione(int n1, int n2) {
            return n1 - n2;
        }

        public int moltiplicazione(int n1, int n2) {
            return n1 * n2;
        }

        public float divisione(float n1, float n2) {
            return n1 / n2;
        }
    }


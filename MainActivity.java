package com.taiwo.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Display
    private TextView tvDisplay;

    // Calculation variables
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String currentOperator = "";
    private boolean isNewNumber = true;

    // Bonus Feature 1: Degree/Radian Toggle
    private boolean isDegreeMode = true;  // true = DEG, false = RAD

    // Bonus Feature 2: Calculation History
    private StringBuilder history = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // Set up all buttons
        setupNumberButtons();
        setupOperatorButtons();
        setupScientificButtons();
        setupFunctionButtons();
        setupModeToggle();
        setupHistoryButton();
    }

    // ==================== NUMBER BUTTONS ====================
    private void setupNumberButtons() {
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        for (int id : numberIds) {
            Button btn = findViewById(id);
            btn.setOnClickListener(v -> {
                Button b = (Button) v;
                String digit = b.getText().toString();

                if (isNewNumber) {
                    tvDisplay.setText(digit);
                    isNewNumber = false;
                } else {
                    tvDisplay.append(digit);
                }
            });
        }

        // Decimal point
        Button btnDecimal = findViewById(R.id.btnDecimal);
        btnDecimal.setOnClickListener(v -> {
            if (!tvDisplay.getText().toString().contains(".")) {
                tvDisplay.append(".");
            }
        });
    }

    // ==================== OPERATOR BUTTONS ====================
    private void setupOperatorButtons() {
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            firstNumber = Double.parseDouble(tvDisplay.getText().toString());
            currentOperator = "+";
            isNewNumber = true;
        });

        Button btnSubtract = findViewById(R.id.btnSubtract);
        btnSubtract.setOnClickListener(v -> {
            firstNumber = Double.parseDouble(tvDisplay.getText().toString());
            currentOperator = "-";
            isNewNumber = true;
        });

        Button btnMultiply = findViewById(R.id.btnMultiply);
        btnMultiply.setOnClickListener(v -> {
            firstNumber = Double.parseDouble(tvDisplay.getText().toString());
            currentOperator = "×";
            isNewNumber = true;
        });

        Button btnDivide = findViewById(R.id.btnDivide);
        btnDivide.setOnClickListener(v -> {
            firstNumber = Double.parseDouble(tvDisplay.getText().toString());
            currentOperator = "÷";
            isNewNumber = true;
        });
    }

    // ==================== SCIENTIFIC FUNCTIONS ====================
    private void setupScientificButtons() {
        // Sin (with DEG/RAD support)
        Button btnSin = findViewById(R.id.btnSin);
        btnSin.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            double result;
            if (isDegreeMode) {
                result = Math.sin(Math.toRadians(value));
            } else {
                result = Math.sin(value);
            }
            String calculation = "sin(" + value + ") = " + result;
            tvDisplay.setText(String.valueOf(result));
            addToHistory(calculation);
            isNewNumber = true;
        });

        // Cos (with DEG/RAD support)
        Button btnCos = findViewById(R.id.btnCos);
        btnCos.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            double result;
            if (isDegreeMode) {
                result = Math.cos(Math.toRadians(value));
            } else {
                result = Math.cos(value);
            }
            String calculation = "cos(" + value + ") = " + result;
            tvDisplay.setText(String.valueOf(result));
            addToHistory(calculation);
            isNewNumber = true;
        });

        // Tan (with DEG/RAD support)
        Button btnTan = findViewById(R.id.btnTan);
        btnTan.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            double result;
            if (isDegreeMode) {
                result = Math.tan(Math.toRadians(value));
            } else {
                result = Math.tan(value);
            }
            String calculation = "tan(" + value + ") = " + result;
            tvDisplay.setText(String.valueOf(result));
            addToHistory(calculation);
            isNewNumber = true;
        });

        // Log (base 10)
        Button btnLog = findViewById(R.id.btnLog);
        btnLog.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            if (value > 0) {
                double result = Math.log10(value);
                String calculation = "log(" + value + ") = " + result;
                tvDisplay.setText(String.valueOf(result));
                addToHistory(calculation);
            } else {
                tvDisplay.setText("Error: log(0)");
            }
            isNewNumber = true;
        });

        // Natural Log (ln)
        Button btnLn = findViewById(R.id.btnLn);
        btnLn.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            if (value > 0) {
                double result = Math.log(value);
                String calculation = "ln(" + value + ") = " + result;
                tvDisplay.setText(String.valueOf(result));
                addToHistory(calculation);
            } else {
                tvDisplay.setText("Error: ln(0)");
            }
            isNewNumber = true;
        });

        // Exponential (e^x)
        Button btnExp = findViewById(R.id.btnExp);
        btnExp.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            double result = Math.exp(value);
            String calculation = "e^" + value + " = " + result;
            tvDisplay.setText(String.valueOf(result));
            addToHistory(calculation);
            isNewNumber = true;
        });

        // Square Root
        Button btnSqrt = findViewById(R.id.btnSqrt);
        btnSqrt.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            if (value >= 0) {
                double result = Math.sqrt(value);
                String calculation = "√" + value + " = " + result;
                tvDisplay.setText(String.valueOf(result));
                addToHistory(calculation);
            } else {
                tvDisplay.setText("Error: √(-1)");
            }
            isNewNumber = true;
        });

        // Square (x²)
        Button btnSquare = findViewById(R.id.btnSquare);
        btnSquare.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            double result = value * value;
            String calculation = value + "² = " + result;
            tvDisplay.setText(String.valueOf(result));
            addToHistory(calculation);
            isNewNumber = true;
        });

        // Cube (x³)
        Button btnCube = findViewById(R.id.btnCube);
        btnCube.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            double result = value * value * value;
            String calculation = value + "³ = " + result;
            tvDisplay.setText(String.valueOf(result));
            addToHistory(calculation);
            isNewNumber = true;
        });

        // Cube Root (∛)
        Button btnCubeRoot = findViewById(R.id.btnCubeRoot);
        btnCubeRoot.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            if (value >= 0) {
                double result = Math.cbrt(value);
                String calculation = "∛" + value + " = " + result;
                tvDisplay.setText(String.valueOf(result));
                addToHistory(calculation);
            } else {
                tvDisplay.setText("Error: ∛(-1)");
            }
            isNewNumber = true;
        });

        // Factorial
        Button btnFactorial = findViewById(R.id.btnFactorial);
        btnFactorial.setOnClickListener(v -> {
            double value = Double.parseDouble(tvDisplay.getText().toString());
            if (value >= 0 && value == Math.floor(value) && value <= 170) {
                double result = factorial((int) value);
                String calculation = value + "! = " + result;
                tvDisplay.setText(String.valueOf(result));
                addToHistory(calculation);
            } else if (value > 170) {
                tvDisplay.setText("Error: Number too large");
            } else {
                tvDisplay.setText("Error: ! only for integers");
            }
            isNewNumber = true;
        });

        // Pi (π)
        Button btnPi = findViewById(R.id.btnPi);
        btnPi.setOnClickListener(v -> {
            tvDisplay.setText(String.valueOf(Math.PI));
            isNewNumber = true;
        });
    }

    // ==================== FUNCTION BUTTONS ====================
    private void setupFunctionButtons() {
        // Equals button
        Button btnEquals = findViewById(R.id.btnEquals);
        btnEquals.setOnClickListener(v -> calculateResult());

        // Clear button
        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(v -> {
            tvDisplay.setText("0");
            firstNumber = 0;
            secondNumber = 0;
            currentOperator = "";
            isNewNumber = true;
        });
    }

    // ==================== BONUS: DEG/RAD TOGGLE ====================
    private void setupModeToggle() {
        Button btnModeToggle = findViewById(R.id.btnModeToggle);
        btnModeToggle.setOnClickListener(v -> {
            isDegreeMode = !isDegreeMode;
            btnModeToggle.setText(isDegreeMode ? "DEG" : "RAD");
            tvDisplay.setText(isDegreeMode ? "DEG Mode" : "RAD Mode");
            isNewNumber = true;
        });
    }

    // ==================== BONUS: HISTORY ====================
    private void setupHistoryButton() {
        Button btnHistory = findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(v -> openHistory());
    }

    private void addToHistory(String calculation) {
        if (history.length() > 0) {
            history.append("\n");
        }
        history.append(calculation);
    }

    private void openHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("history", history.toString());
        startActivity(intent);
    }

    // ==================== CORE CALCULATION ====================
    private void calculateResult() {
        secondNumber = Double.parseDouble(tvDisplay.getText().toString());
        double result = 0;
        boolean error = false;
        String calculation = "";

        switch (currentOperator) {
            case "+":
                result = firstNumber + secondNumber;
                calculation = firstNumber + " + " + secondNumber + " = " + result;
                break;
            case "-":
                result = firstNumber - secondNumber;
                calculation = firstNumber + " - " + secondNumber + " = " + result;
                break;
            case "×":
                result = firstNumber * secondNumber;
                calculation = firstNumber + " × " + secondNumber + " = " + result;
                break;
            case "÷":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                    calculation = firstNumber + " ÷ " + secondNumber + " = " + result;
                } else {
                    tvDisplay.setText("Error: ÷ by 0");
                    error = true;
                }
                break;
            default:
                return;
        }

        if (!error) {
            tvDisplay.setText(String.valueOf(result));
            addToHistory(calculation);
            isNewNumber = true;
        }
    }

    // ==================== FACTORIAL METHOD ====================
    private double factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    // ==================== BONUS: KEYBOARD SUPPORT ====================
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        switch (keyCode) {
            case android.view.KeyEvent.KEYCODE_0:
                appendDigit("0");
                return true;
            case android.view.KeyEvent.KEYCODE_1:
                appendDigit("1");
                return true;
            case android.view.KeyEvent.KEYCODE_2:
                appendDigit("2");
                return true;
            case android.view.KeyEvent.KEYCODE_3:
                appendDigit("3");
                return true;
            case android.view.KeyEvent.KEYCODE_4:
                appendDigit("4");
                return true;
            case android.view.KeyEvent.KEYCODE_5:
                appendDigit("5");
                return true;
            case android.view.KeyEvent.KEYCODE_6:
                appendDigit("6");
                return true;
            case android.view.KeyEvent.KEYCODE_7:
                appendDigit("7");
                return true;
            case android.view.KeyEvent.KEYCODE_8:
                appendDigit("8");
                return true;
            case android.view.KeyEvent.KEYCODE_9:
                appendDigit("9");
                return true;
            case android.view.KeyEvent.KEYCODE_ENTER:
                calculateResult();
                return true;
            case android.view.KeyEvent.KEYCODE_DEL:
                String text = tvDisplay.getText().toString();
                if (text.length() > 1) {
                    tvDisplay.setText(text.substring(0, text.length() - 1));
                } else {
                    tvDisplay.setText("0");
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private void appendDigit(String digit) {
        if (isNewNumber) {
            tvDisplay.setText(digit);
            isNewNumber = false;
        } else {
            tvDisplay.append(digit);
        }
    }
}
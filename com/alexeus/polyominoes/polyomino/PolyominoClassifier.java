package com.alexeus.polyominoes.polyomino;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by alexeus on 01.02.2017.
 * Класс, который создаёт виды полиомино и служит для их классификации
 */
public class PolyominoClassifier {

    private static PolyominoClassifier instance = new PolyominoClassifier();

    public final static int MAX_POLYOMINO_SIZE = 6;

    private ArrayList<ArrayList<Polyomino>> polyominoes;

    private ArrayList<ArrayList<ArrayList<PolyominoForm>>> polyominoForms;

    private TreeMap<Long, PolyominoForm> formForCode;

    private int[][] lineCodeShifts;

    private static final String[][] polyominoNames = {
            {},
            {"Квадрат"},
            {"Домино"},
            {"I", "L"},
            {"I", "L", "Т", "Z", "O"},
            {"I", "L", "Y", "N", "V", "T", "P", "F", "C", "Z", "X", "W"},
            {"Палка", "Клюшка", "Ветка", "Сучок", "Посох", "Уголок", "Обелиск", "Ложка", "Распутье", "Автомат",
            "Верующий", "Мост", "Перископ", "Регулировщик", "Крест", "Шляпа", "Коралл", "Река", "Пистолет", "Ловец",
            "Кобура", "Жаба", "Колючка", "Башмак", "Змея", "Кобра", "Крюк", "Лестница", "Пьедестал", "Стул",
            "Рыбка", "Прямоугольник", "Ковш", "Стрелялка", "Хошелка"},
            {"Палка", "Клюшка", "Ветка", "Сучок", "Посох", "Г", "Обелиск", "Половник", "Распутье", "Винтовка",
            "Патриарх", "Миномёт", "Епископ", "Мост", "Перископ", "Регулировщик", "Крест", "Флаг", "Шипы", "Скейт",
            "Побеги", "Побочность", "Плюс"}};

    private PolyominoClassifier() {
        polyominoes = new ArrayList<>();
        polyominoForms = new ArrayList<>();
        formForCode = new TreeMap<>();
        lineCodeShifts = new int[MAX_POLYOMINO_SIZE + 1][];
        for (int i = 0; i <= MAX_POLYOMINO_SIZE; i++) {
            generatePolyominoes(i);
        }
        System.out.println("Полимины сформированы");
    }

    public static PolyominoClassifier getInstance() {
        return instance;
    }

    private void generatePolyominoes(int n) {
        polyominoes.add(n, new ArrayList<>());
        polyominoForms.add(n, new ArrayList<>());
        if (n == 0) return;
        int[] x = new int[n];
        int[] y = new int[n];
        lineCodeShifts[n] = new int[n];
        for (int i = 0; i < n; i++) {
            lineCodeShifts[n][i] = i == 0 ? 0 : lineCodeShifts[n][i - 1] + n - (i - 1);
        }
        if (n == 1) {
            x[0] = 0;
            y[0] = 0;
            polyominoes.get(n).add(new Polyomino(n, 0));
            polyominoForms.get(n).add(new ArrayList<>());
            polyominoForms.get(n).get(0).add(new PolyominoForm(n, 0, 0, x, y));
            formForCode.put(0L, polyominoForms.get(1).get(0).get(0));
        } else if (n > 1) {
            int[] changedX, changedY;
            changedX = new int[n];
            changedY = new int[n];
            long code, newCode;
            int maxY;
            int numPolyomino = 0;
            // Цикл по всем полиминам размером на единицу меньше
            for (int polyIndex = 0; polyIndex < polyominoes.get(n - 1).size(); polyIndex++) {
                PolyominoForm curForm = polyominoForms.get(n - 1).get(polyIndex).get(0);
                System.arraycopy(curForm.getX(), 0, x, 0, n - 1);
                System.arraycopy(curForm.getY(), 0, y, 0, n - 1);
                // Пробуем составить новые формы. Последний квадрат возим по прямоугольнику предыдущих квадратов
                for (y[n - 1] = curForm.getMaxY() + 1; y[n - 1] >= -1; y[n - 1]--) {
                    for (x[n - 1] = curForm.getMaxX() + 1; x[n - 1] >= -1; x[n - 1]--) {
                        boolean isConnected = false;
                        boolean isOccupied = false;
                        for (int i = 0; i < n - 1; i++) {
                            if (x[i] == x[n - 1] && y[i] == y[n - 1]) {
                                isOccupied = true;
                                break;
                            }
                            if(Math.abs(x[i] - x[n - 1]) + Math.abs(y[i] - y[n - 1]) == 1) {
                                isConnected = true;
                            }
                        }
                        if (!isOccupied && isConnected) {
                            // Ура, мы нашли валидную форму! Проверяем, находили ли мы её раньше
                            maxY = 0;
                            for (int i = 0; i < n; i++) {
                                changedX[i] = x[i] + (x[n - 1] < 0 ? 1 : 0);
                                changedY[i] = y[i] + (y[n - 1] < 0 ? 1 : 0);
                                if (changedY[i] > maxY) {
                                    maxY = changedY[i];
                                }
                            }
                            code = getCode(n, changedX, changedY);
                            if (!formForCode.containsKey(code)) {
                                // Новое полиомино найдено. Создаём её новые формы.
                                polyominoes.get(n).add(new Polyomino(n, numPolyomino));
                                // Нулевая форма
                                polyominoForms.get(n).add(numPolyomino, new ArrayList<>());
                                int numForms = 0;
                                polyominoForms.get(n).get(numPolyomino).add(new PolyominoForm(n, numPolyomino, numForms, changedX, changedY));
                                formForCode.put(code, polyominoForms.get(n).get(numPolyomino).get(0));
                                // Пробуем создать остальные формы
                                for (int side = 0; side < 2; side++) {
                                    do {
                                        int newMaxY = 0;
                                        // Поворачиваем найденную форму по часовой стрелке
                                        for (int i = 0; i < n; i++) {
                                            int oldY = changedY[i];
                                            changedY[i] = changedX[i];
                                            changedX[i] = maxY - oldY;
                                            if (changedY[i] > newMaxY) {
                                                newMaxY = changedY[i];
                                            }
                                        }
                                        maxY = newMaxY;
                                        newCode = getCode(n, changedX, changedY);
                                        if (!formForCode.containsKey(newCode)) {
                                            numForms++;
                                            polyominoForms.get(n).get(numPolyomino).add(new PolyominoForm(
                                                    n, numPolyomino, numForms, changedX, changedY));
                                            formForCode.put(newCode, polyominoForms.get(n).get(numPolyomino).get(numForms));
                                        }
                                    } while(code != newCode);
                                    if (side == 0) {
                                        // Отражаем форму
                                        for (int i = 0; i < n; i++) {
                                            changedY[i] = maxY - changedY[i];
                                        }
                                        code = getCode(n, changedX, changedY);
                                        if (!formForCode.containsKey(code)) {
                                            numForms++;
                                            polyominoForms.get(n).get(numPolyomino).add(new PolyominoForm(
                                                    n, numPolyomino, numForms, changedX, changedY));
                                            formForCode.put(code, polyominoForms.get(n).get(numPolyomino).get(numForms));
                                        } else break;
                                    }
                                }
                                numPolyomino++;
                            }
                        }
                    }
                }
            }
        }
    }

    static String getName(int n, int id) {
        return polyominoNames.length > n && polyominoNames[n].length > id ? polyominoNames[n][id] :
                ("Безымянность №" + (id + 1));
    }

    private long getCode(int n, int[] x, int[] y) {
        long code = 0;
        for (int i = 0; i < n; i++) {
            code += 1 << (lineCodeShifts[n][x[i]] + y[i]);
        }
        return code;
    }

    public PolyominoForm detectForm(int n, int[] x, int[] y) {
        long code = getCode(n, x, y);
        return formForCode.containsKey(code) ? formForCode.get(code) : null;
    }

    public PolyominoForm getForm(int n, int id, int numForm) {
        return polyominoForms.get(n).get(id).get(numForm);
    }

    public int getNumPolyominos(int n) {
        return polyominoes.get(n).size();
    }

    public int getNumPolyominoForms(int n, int polyominoIndex) {
        return polyominoForms.get(n).get(polyominoIndex).size();
    }

    ArrayList<Polyomino> getPolyominoesOfSize(int n) {
        return polyominoes.get(n);
    }

    ArrayList<ArrayList<PolyominoForm>> getPolyominoFormsOfSize(int n) {
        return polyominoForms.get(n);
    }

    public void printAllPolyominoes() {
        for (int n = 1; n <= MAX_POLYOMINO_SIZE; n++) {
            for (int polyomino = 0; polyomino < polyominoes.get(n).size(); polyomino++) {
                System.out.println(polyominoes.get(n).get(polyomino));
                for (int form = 0; form < polyominoForms.get(n).get(polyomino).size(); form++) {
                    int[] x = polyominoForms.get(n).get(polyomino).get(form).getX();
                    int[] y = polyominoForms.get(n).get(polyomino).get(form).getY();
                    System.out.print("\tФорма №" + form + ", код: " + getCode(n, x, y) + ". ");
                    for (int i = 0; i < n; i++) {
                        System.out.print((i == 0 ? "" : ", ") + "{" + x[i] + ", " + y[i] + "}");
                    }
                    System.out.println();
                }
            }
        }
    }
}

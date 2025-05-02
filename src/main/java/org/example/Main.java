package org.example;

import org.example.dao.FlyDao;
import org.example.dao.WalkDao;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final FlyDao flyDao = new FlyDao();
    private final WalkDao walkDao = new WalkDao();
    private final Scanner scanner = new Scanner(System.in);

    private Fly selectedFly = null;
    private Walk selectedWalk = null;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Создать наземного врага");
            System.out.println("2. Создать летающего врага");
            System.out.println("3. Просмотр всех врагов");
            System.out.println("4. Выбор врага");
            System.out.println("5. Изменение данных врага");
            System.out.println("6. Действие");
            System.out.println("7. Удаление врага");
            System.out.println("8. Выход");
            System.out.print("Выберите пункт (1-8): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // очистка перевода строки

            switch (choice) {
                case 1 -> createWalkEnemy();
                case 2 -> createFlyEnemy();
                case 3 -> viewAllEnemies();
                case 4 -> selectEnemy();
                case 5 -> updateSelectedEnemy();
                case 6 -> performActionOnSelectedEnemy();
                case 7 -> deleteEnemy();
                case 8 -> {
                    running = false;
                    System.out.println("Выход...");
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void createWalkEnemy() {
        System.out.print("Введите имя наземного врага: ");
        String name = scanner.nextLine();
        System.out.print("Введите HP врага: ");
        int hp = scanner.nextInt();
        System.out.print("Введите урон врага: ");
        int damage = scanner.nextInt();
        System.out.print("Введите защиту врага: ");
        int protection = scanner.nextInt();
        System.out.print("Введите отскок врага: ");
        int rebound = scanner.nextInt();
        scanner.nextLine();

        Walk walk = new Walk(name, hp, damage, protection, rebound);
        walkDao.save(walk);
        System.out.println("Наземный враг создан и сохранён!");
    }

    private void createFlyEnemy() {
        System.out.print("Введите имя летающего врага: ");
        String name = scanner.nextLine();
        System.out.print("Введите HP врага: ");
        int hp = scanner.nextInt();
        System.out.print("Введите урон врага: ");
        int damage = scanner.nextInt();
        System.out.print("Введите кислоту врага: ");
        int spit = scanner.nextInt();
        System.out.print("Введите количество крыльев врага: ");
        int wing = scanner.nextInt();
        scanner.nextLine();

        Fly fly = new Fly(name, hp, damage, spit, wing);
        flyDao.save(fly);
        System.out.println("Летающий враг создан и сохранён!");
    }

    private void viewAllEnemies() {
        List<Fly> flies = flyDao.findAll();
        List<Walk> walks = walkDao.findAll();

        if (flies.isEmpty() && walks.isEmpty()) {
            System.out.println("Нет врагов для отображения.");
        } else {
            System.out.println("\nСписок всех врагов:");
            for (Fly fly : flies) {
                System.out.println(fly.toString());
            }
            for (Walk walk : walks) {
                System.out.println(walk.toString());
            }
        }
    }

    private void selectEnemy() {
        viewAllEnemies();
        System.out.print("Введите ID врага для выбора: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        selectedFly = flyDao.findById(id);
        selectedWalk = null;
        if (selectedFly == null) {
            selectedWalk = walkDao.findById(id);
        }

        if (selectedFly != null) {
            System.out.println("Выбран летающий враг: " + selectedFly.getName());
        } else if (selectedWalk != null) {
            System.out.println("Выбран наземный враг: " + selectedWalk.getName());
        } else {
            System.out.println("Враг с таким ID не найден.");
        }
    }

    private void updateSelectedEnemy() {
        if (selectedFly != null) {
            System.out.print("Новое имя: ");
            selectedFly.setName(scanner.nextLine());
            System.out.print("Новый HP: ");
            selectedFly.setHp(scanner.nextInt());
            System.out.print("Новый урон: ");
            selectedFly.setDamage(scanner.nextInt());
            System.out.print("Новая кислота: ");
            selectedFly.setSpit(scanner.nextInt());
            System.out.print("Новые крылья: ");
            selectedFly.setWing(scanner.nextInt());
            scanner.nextLine();
            flyDao.save(selectedFly);
            System.out.println("Летающий враг обновлён!");

        } else if (selectedWalk != null) {
            System.out.print("Новое имя: ");
            selectedWalk.setName(scanner.nextLine());
            System.out.print("Новый HP: ");
            selectedWalk.setHp(scanner.nextInt());
            System.out.print("Новый урон: ");
            selectedWalk.setDamage(scanner.nextInt());
            System.out.print("Новая защита: ");
            selectedWalk.setProtectionValue(scanner.nextInt());
            System.out.print("Новый отскок: ");
            selectedWalk.setRebound(scanner.nextInt());
            scanner.nextLine();
            walkDao.save(selectedWalk);
            System.out.println("Наземный враг обновлён!");

        } else {
            System.out.println("Сначала выберите врага (пункт 4).");
        }
    }

    private void performActionOnSelectedEnemy() {
        if (selectedFly == null && selectedWalk == null) {
            System.out.println("Сначала выберите врага (пункт 4).");
            return;
        }

        System.out.println("\nВыберите действие:");
        System.out.println("1. Атака");
        System.out.println("2. Буум");
        System.out.println("3. Движение");
        System.out.println("4. Клонирование");

        if (selectedFly != null) {
            System.out.println("5. Регенерация");
            System.out.println("6. Ускорение");
        } else {
            System.out.println("5. Защита");
            System.out.println("6. Отскок");
        }

        System.out.print("Выберите действие (1-6): ");
        int action = scanner.nextInt();
        scanner.nextLine();

        String result;

        if (selectedFly != null) {
            result = switch (action) {
                case 1 -> selectedFly.attack();
                case 2 -> selectedFly.bum();
                case 3 -> selectedFly.movement();
                case 4 -> selectedFly.makeClone();
                case 5 -> selectedFly.regeneration();
                case 6 -> selectedFly.bust();
                default -> {
                    System.out.println("Неверный выбор."); yield null;
                }
            };
        } else {
            result = switch (action) {
                case 1 -> selectedWalk.attack();
                case 2 -> selectedWalk.bum();
                case 3 -> selectedWalk.movement();
                case 4 -> selectedWalk.makeClone();
                case 5 -> selectedWalk.protection();
                case 6 -> selectedWalk.reboundAction();
                default -> {
                    System.out.println("Неверный выбор."); yield null;
                }
            };
        }

        if (result != null) {
            System.out.println("Результат действия: " + result);
        }
    }

    private void deleteEnemy() {
        viewAllEnemies();

        System.out.print("Введите ID врага для удаления: ");
        int idToDelete = scanner.nextInt();
        scanner.nextLine();

        boolean deleted = false;

        if (flyDao.existsById(idToDelete)) {
            flyDao.deleteById(idToDelete);
            deleted = true;
        } else if (walkDao.existsById(idToDelete)) {
            walkDao.deleteById(idToDelete);
            deleted = true;
        }

        if (deleted) {
            System.out.println("Враг с ID " + idToDelete + " удалён.");
            if (selectedFly != null && selectedFly.getId() == idToDelete) selectedFly = null;
            if (selectedWalk != null && selectedWalk.getId() == idToDelete) selectedWalk = null;
        } else {
            System.out.println("Враг с таким ID не найден.");
        }
    }
}

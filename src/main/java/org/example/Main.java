package org.example;

import org.example.repository.FlyRepository;
import org.example.repository.WalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan(basePackages = "org.example")
@EnableJpaRepositories(basePackages = "org.example.repository")
@EntityScan(basePackages = "org.example")
public class Main implements CommandLineRunner {
    private static final Scanner scanner = new Scanner(System.in);

    @Autowired
    private FlyRepository flyRepository;

    @Autowired
    private WalkRepository walkRepository;

    private Fly selectedFly = null;
    private Walk selectedWalk = null;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
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
            System.out.print("Выберите пункт (1-7): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // очистка перевода строки

            switch (choice) {
                case 1:
                    createWalkEnemy();  // Создаем наземного врага
                    break;
                case 2:
                    createFlyEnemy();   // Создаем летающего врага
                    break;
                case 3:
                    viewAllEnemies();   // Просмотр всех врагов
                    break;
                case 4:
                    selectEnemy(); // Выбор врага 
                    break;
                case 5:
                    updateSelectedEnemy(); // Изменение данных врага
                    break;
                case 6:
                    performActionOnSelectedEnemy(); // Действие
                    break;
                case 7:
                    deleteEnemy(); // Удаление врага
                    break;
                case 8:
                    running = false;
                    System.out.println("Выход...");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    // 1. Создание наземного врага
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
        scanner.nextLine(); // очистка перевода строки

        Walk walk = new Walk(name, hp, damage, protection, rebound);
        walkRepository.save(walk);  // Сохраняем в базе данных

        System.out.println("Наземный враг создан и сохранён!");
    }

    // 2. Создание летающего врага
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
        scanner.nextLine(); // очистка перевода строки

        Fly fly = new Fly(name, hp, damage, spit, wing);
        flyRepository.save(fly);  // Сохраняем в базе данных

        System.out.println("Летающий враг создан и сохранён!");
    }

    // 3. Просмотр врагов
    private void viewAllEnemies() {
        List<Fly> flies = flyRepository.findAll();  // Получаем все объекты Fly
        List<Walk> walks = walkRepository.findAll(); // Получаем все объекты Walk

        if (flies.isEmpty() && walks.isEmpty()) {
            System.out.println("Нет врагов для отображения.");
        } else {
            System.out.println("\nСписок всех врагов:");

            // Выводим летающих врагов
            for (Fly fly : flies) {
                System.out.println("ID: " + fly.getId() + ", Имя: " + fly.getName() + ", HP: " + fly.getHp() + ", Урон: " + fly.getDamage() +
                        ", Кислота: " + fly.getSpit() + ", Крылья: " + fly.getWing());
            }

            // Выводим наземных врагов
            for (Walk walk : walks) {
                System.out.println("ID: " + walk.getId() + ", Имя: " + walk.getName() + ", HP: " + walk.getHp() + ", Урон: " + walk.getDamage() +
                        ", Защита: " + walk.getProtectionValue() + ", Отскок: " + walk.getRebound());
            }
        }
    }

    // 4. Выбор врага
    private void selectEnemy() {
        viewAllEnemies();
        System.out.print("Введите ID врага для выбора: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Очистка ввода
    
        selectedFly = flyRepository.findById(id).orElse(null);
        selectedWalk = null;
        if (selectedFly == null) {
            selectedWalk = walkRepository.findById(id).orElse(null);
        }
    
        if (selectedFly != null) {
            System.out.println("Выбран летающий враг: " + selectedFly.getName());
        } else if (selectedWalk != null) {
            System.out.println("Выбран наземный враг: " + selectedWalk.getName());
        } else {
            System.out.println("Враг с таким ID не найден.");
        }
    }

    // 5. Изменение данных врага
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
            scanner.nextLine(); // очистка
            flyRepository.save(selectedFly);
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
            scanner.nextLine(); // очистка
            walkRepository.save(selectedWalk);
            System.out.println("Наземный враг обновлён!");
    
        } else {
            System.out.println("Сначала выберите врага (пункт 4).");
        }
    }
    
    // 6. Действие
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
        }
        else {
            System.out.println("5. Защита");
            System.out.println("6. Отскок");
        }
    
        System.out.print("Выберите действие (1-6): ");
        int action = scanner.nextInt();
        scanner.nextLine(); // очистка
    
        String result = "";
    
        if (selectedFly != null) {
            switch (action) {
                case 1: result = selectedFly.attack(); break;
                case 2: result = selectedFly.bum(); break;
                case 3: result = selectedFly.movement(); break;
                case 4: result = selectedFly.makeClone(); break;
                case 5: result = selectedFly.regeneration(); break;
                case 6: result = selectedFly.bust(); break;
                default: System.out.println("Неверный выбор."); return;
            }
        } else if (selectedWalk != null) {
            switch (action) {
                case 1: result = selectedWalk.attack(); break;
                case 2: result = selectedWalk.bum(); break;
                case 3: result = selectedWalk.movement(); break;
                case 4: result = selectedWalk.makeClone(); break;
                case 5: result = selectedWalk.protection(); break;
                case 6: result = selectedWalk.reboundAction(); break;
                default: System.out.println("Неверный выбор."); return;
            }
        }
    
        System.out.println("Результат действия: " + result);
    } 
    
    // 7. Удаление врага
    private void deleteEnemy() {
        viewAllEnemies(); // Показать всех врагов
    
        System.out.print("Введите ID врага для удаления: ");
        int idToDelete = scanner.nextInt();
        scanner.nextLine(); // очистка
    
        boolean deleted = false;
    
        if (flyRepository.existsById(idToDelete)) {
            flyRepository.deleteById(idToDelete);
            deleted = true;
        } else if (walkRepository.existsById(idToDelete)) {
            walkRepository.deleteById(idToDelete);
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

package com.streaming;

import com.streaming.controllers.*;
import com.streaming.utils.DependencyInjector;
import com.streaming.interfaces.*;
import com.streaming.models.*;

public class StreamingPlatformApp {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   Plataforma de Streaming Multimedia - Backend Demo         ║");
        System.out.println("║   (DIP - Dependency Inversion Principle aplicado)           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        UserManageable userService = DependencyInjector.getUserService();
        ContentManageable contentService = DependencyInjector.getContentService();
        StreamPlayable streamService = DependencyInjector.getStreamService();
        SubscriptionManageable subscriptionService = DependencyInjector.getSubscriptionService();
        StreamValidator streamValidator = DependencyInjector.getStreamValidator();
        NotificationService notificationService = DependencyInjector.getNotificationService();

        UserController userController = new UserController(userService, notificationService);
        ContentController contentController = new ContentController(contentService);
        PlayController playController = new PlayController(
            streamService, streamValidator, userService, 
            contentService, subscriptionService, notificationService
        );
        SubscriptionController subscriptionController = new SubscriptionController(subscriptionService);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("1. REGISTRANDO USUARIOS");
        System.out.println("=".repeat(60));
        User user1 = userController.registerUser("juan_perez", "juan@example.com", "pass123");
        User user2 = userController.registerUser("maria_garcia", "maria@example.com", "pass456");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("2. AGREGANDO CONTENIDO");
        System.out.println("=".repeat(60));
        Content movie1 = contentController.addContent(
            "Inception", 
            "A skilled thief steals corporate secrets", 
            "Sci-Fi", "movie", 148L, "https://stream.example.com/inception"
        );
        Content series1 = contentController.addContent(
            "Breaking Bad",
            "A chemistry teacher turns to cooking meth",
            "Drama",
            "series",
            50L,
            "https://stream.example.com/breakingbad"
        );
        Content movie2 = contentController.addContent(
            "The Matrix",
            "A computer hacker discovers reality is a simulation",
            "Sci-Fi",
            "movie",
            136L,
            "https://stream.example.com/thematrix"
        );

        System.out.println("\n" + "=".repeat(60));
        System.out.println("3. VISUALIZANDO PLANES DE SUSCRIPCIÓN");
        System.out.println("=".repeat(60));
        subscriptionController.listAllPlans();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("4. USUARIO PLAN FREE - INTENTANDO VER CON CALIDAD 4K");
        System.out.println("=".repeat(60));
        System.out.println("Usuario: juan_perez (Plan: Free)");
        subscriptionController.displayPlanDetails("Free");
        try {
            Stream stream1 = playController.playContent(
                user1.getId(), 
                movie1.getId(), 
                "4K", 
                "Smart TV"
            );
            System.out.println("[SUCCESS] Stream iniciado: " + stream1.getId());
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("5. UPGRADE A PLAN PREMIUM");
        System.out.println("=".repeat(60));
        userController.upgradeSubscription(user1.getId(), "Premium");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("6. REPRODUCIENDO CONTENIDO CON PLAN PREMIUM");
        System.out.println("=".repeat(60));
        System.out.println("Usuario: juan_perez (Plan: Premium)");
        subscriptionController.displayPlanDetails("Premium");
        Stream stream2 = playController.playContent(
            user1.getId(), 
            movie1.getId(), 
            "4K", 
            "Smart TV"
        );

        System.out.println("\n" + "=".repeat(60));
        System.out.println("7. USUARIO BASIC REPRODUCIENDO");
        System.out.println("=".repeat(60));
        userController.upgradeSubscription(user2.getId(), "Basic");
        System.out.println("Usuario: maria_garcia (Plan: Basic)");
        subscriptionController.displayPlanDetails("Basic");
        Stream stream3 = playController.playContent(
            user2.getId(),
            series1.getId(),
            "HD",
            "Mobile"
        );

        System.out.println("\n" + "=".repeat(60));
        System.out.println("8. ACTUALIZANDO PROGRESO DE VISUALIZACIÓN");
        System.out.println("=".repeat(60));
        playController.updateWatchProgress(stream2.getId(), 45L);
        playController.updateWatchProgress(stream3.getId(), 25L);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("9. RATING DE CONTENIDO");
        System.out.println("=".repeat(60));
        contentController.rateContent(movie1.getId(), 5);
        contentController.rateContent(series1.getId(), 4);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("10. BÚSQUEDA DE CONTENIDO");
        System.out.println("=".repeat(60));
        System.out.println("Searching for 'matrix':");
        contentController.searchContent("matrix");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("11. HISTORIAL DE VISUALIZACIÓN");
        System.out.println("=".repeat(60));
        System.out.println("Watch history for user " + user1.getId() + ":");
        playController.getWatchHistory(user1.getId()).forEach(s -> 
            System.out.println("  - Stream ID: " + s.getId() + 
                             ", Content: " + s.getContentId() + 
                             ", Quality: " + s.getQuality())
        );

        System.out.println("\n" + "=".repeat(60));
        System.out.println("12. CONTENIDO POR GÉNERO");
        System.out.println("=".repeat(60));
        System.out.println("Sci-Fi content:");
        contentController.getContentByGenre("Sci-Fi").forEach(c ->
            System.out.println("  - " + c.getTitle() + " (Rating: " + c.getRating() + "/5)")
        );

        System.out.println("\n" + "=".repeat(60));
        System.out.println("13. DETENIENDO REPRODUCCIÓN");
        System.out.println("=".repeat(60));
        playController.stopPlayback(stream2.getId());
        playController.stopPlayback(stream3.getId());

        System.out.println("\n" + "=".repeat(60));
        System.out.println("14. LISTADO DE USUARIOS");
        System.out.println("=".repeat(60));
        userController.listAllUsers();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("15. LISTADO DE CONTENIDO");
        System.out.println("=".repeat(60));
        contentController.listAllContent();

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   Demo completado exitosamente                              ║");
        System.out.println("║   Principio DIP aplicado en toda la arquitectura            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
}

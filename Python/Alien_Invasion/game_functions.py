import sys
import pygame
from bullet import Bullet
from alien import Alien
from time import sleep


def check_events(ai_settings, screen, stats, play_button, ship, bullets, aliens):
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        elif event.type == pygame.KEYDOWN:
            check_keydown_events(event, ai_settings, screen, ship, bullets)
        elif event.type == pygame.KEYUP:
            check_keyup_events(event, ai_settings, screen, ship, bullets)
        elif event.type == pygame.MOUSEBUTTONDOWN:
            mouse_x, mouse_y = pygame.mouse.get_pos()
            check_play_button(ai_settings, screen, stats, play_button, mouse_x, mouse_y, ship, bullets, aliens)


def update_screen(ai_settings, screen, stats, ship, bullets, aliens, play_button):
    screen.fill(ai_settings.bg_color)
    ship.blitme()
    for bullet in bullets:
        bullet.draw_bullet()
    for alien in aliens:
        alien.blitme()

    if not stats.game_active:
        play_button.draw_button()

    pygame.display.flip()


def check_keyup_events(event, ai_settings, screen, ship, bullets):
    if event.key == pygame.K_RIGHT:
        ship.moving_right = False
    elif event.key == pygame.K_LEFT:
        ship.moving_left = False


def check_keydown_events(event, ai_settings, screen, ship, bullets):
    if event.key == pygame.K_RIGHT:
        ship.moving_right = True
    elif event.key == pygame.K_LEFT:
        ship.moving_left = True
    elif event.key == pygame.K_SPACE:
        if len(bullets) < ai_settings.bullet_max:
            new_bullet = Bullet(ai_settings, screen, ship)
            bullets.add(new_bullet)


def check_collisions(ai_settings, screen, ship, aliens, bullets):
    collisions = pygame.sprite.groupcollide(bullets, aliens, False, True)

    if len(aliens) == 0:
        bullets.empty()
        create_fleet(ai_settings, screen, ship, aliens)


def update_bullets(ai_settings, screen, ship, aliens, bullets):
    """update the postion of the bullets and delete those disappear bullets"""
    bullets.update()
    for bullet in bullets.copy():
        if bullet.rect.bottom <= 0:
            bullets.remove(bullet)
    check_collisions(ai_settings, screen, ship, aliens, bullets)


def create_fleet(ai_settings, screen, ship, aliens):
    alien = Alien(ai_settings, screen)
    num_of_cols = get_number_of_aliens_per_line(ai_settings, alien.rect.width)
    num_of_rows = get_number_rows(ai_settings, ship.rect.height, alien.rect.height)

    for row in range(num_of_rows):
        for col in range(num_of_cols):
            create_an_alien(ai_settings, screen, aliens, row, col)


def get_number_of_aliens_per_line(ai_settings, alien_width):
    available_space_x = ai_settings.screen_width - 2 * alien_width
    return int(available_space_x / (2 * alien_width))


def create_an_alien(ai_settings, screen, aliens, row_num, column_num):
    alien = Alien(ai_settings, screen)
    alien_width = alien.rect.width
    alien.x = alien_width + 2 * alien_width * column_num
    alien.rect.x = alien.x
    alien.rect.y = alien.rect.height + 2 * alien.rect.height * row_num
    aliens.add(alien)


def get_number_rows(ai_settings, ship_height, alien_height):
    """compute num of aliens rows that the screen can hold"""
    available_space_y = ai_settings.screen_height - 3 * alien_height - ship_height
    return int(available_space_y / (2 * alien_height))


def ship_hit(ai_settings, stats, screen, ship, aliens, bullets):
    stats.ships_left -= 1

    if stats.ships_left == 0:
        stats.game_active = False
    else:
        aliens.empty()
        bullets.empty()
        create_fleet(ai_settings, screen, ship, aliens)
        ship.center_ship()
        sleep(1)


def update_aliens(ai_settings, stats, screen, ship, aliens, bullets):
    check_aliens_edge(ai_settings, aliens)
    aliens.update()

    if pygame.sprite.spritecollideany(ship, aliens):
        ship_hit(ai_settings, stats, screen, ship, aliens, bullets)

    check_aliens_bottom(ai_settings, stats, screen, ship, aliens, bullets)


def change_fleet_direction(ai_settings, aliens):
    for alien in aliens.sprites():
        alien.rect.y += ai_settings.fleet_drop_speed
    ai_settings.alien_fleet_direction = ai_settings.alien_fleet_direction * -1


def check_aliens_edge(ai_settings, aliens):
    """If any alien reach the edge, reverse the direction of the aliens and move down them as a whole"""
    for alien in aliens:
        if alien.check_edges():
            change_fleet_direction(ai_settings, aliens)
            break


def check_aliens_bottom(ai_settings, stats, screen, ship, aliens, bullets):
    """judge if any aliens reach the bottom"""
    screen_rect = screen.get_rect()
    for alien in aliens.sprites():
        if alien.rect.bottom >= screen_rect.bottom:
            ship_hit(ai_settings, stats, screen, ship, aliens, bullets)
            break


def check_play_button(ai_settings, screen, stats, play_button, mouse_x, mouse_y, ship, bullets, aliens):
    if play_button.rect.collidepoint(mouse_x, mouse_y):
        stats.reset_stats()
        stats.game_active = True

        aliens.empty()
        bullets.empty()
        create_fleet(ai_settings, screen, ship, aliens)
        ship.center_ship()

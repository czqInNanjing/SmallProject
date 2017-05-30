import pygame
from pygame.sprite import Sprite


class Alien(Sprite):
    def __init__(self, ai_settings, screen):
        super().__init__()

        self.ai_setting = ai_settings
        self.screen = screen

        self.image = pygame.image.load_basic('images/alien.bmp')
        self.rect = self.image.get_rect()

        self.rect.x = self.rect.width
        self.rect.y = self.rect.height

        self.x = self.rect.x

    def blitme(self):
        self.screen.blit(self.image, self.rect)

    def update(self):
        self.x += self.ai_setting.alien_speed * self.ai_setting.alien_fleet_direction
        self.rect.x = self.x

    def check_edges(self):
        """if the alien locates at the edge of the screen, return True"""
        screen_rect = self.screen.get_rect()
        if self.rect.right >= screen_rect.right:
            return True
        elif self.rect.left <= 0:
            return True

        return False


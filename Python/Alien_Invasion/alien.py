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


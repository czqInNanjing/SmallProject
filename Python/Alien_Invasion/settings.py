class Settings():
    def __init__(self):
        # Screen Setting
        self.screen_width = 1200
        self.screen_height = 800
        self.bg_color = (230, 230, 230)
        self.caption = "Alien Invasion"
        self.ship_limit = 3
        self.ship_speed = 10

        # Bullet Setting
        self.bullet_width = 300
        self.bullet_height = 5
        self.bullet_speed = 2
        self.bullet_color = (230, 50, 230)
        self.bullet_max = 10


        # Alien Setting
        self.alien_speed = 1
        self.alien_fleet_direction = 1    # 1 refers to right and -1 refers to left
        self.fleet_drop_speed = 50
class GameStats():
    """Track the game's statistic"""

    def __init__(self, ai_settings):
        """initialize the statistic"""
        self.game_active = False
        self.ai_settings = ai_settings
        self.reset_stats()

    def reset_stats(self):
        self.ships_left = self.ai_settings.ship_limit
import csv
from io import StringIO
from datetime import datetime


class CombatLogger:
    def __init__(self):
        self.csv_data = StringIO()

        self.writer = csv.writer(self.csv_data)

        self.writer.writerow([
            "timestamp",
            "attacker",
            "defender",
            "damage_type",
            "net_damage"
        ])

    def log_attack(self, attacker, defender, damage_type, net_damage):
        self.writer.writerow([
            datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
            attacker,
            defender,
            damage_type,
            net_damage
        ])

    def get_csv(self):
        return self.csv_data.getvalue()
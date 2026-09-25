import random
from logging_system import CombatLogger


class Character:
    def __init__(self, name, health, attack, damage_type, logger=None):
        self.name = name
        self.health = health
        self.max_health = health
        self.attack = attack
        self.damage_type = damage_type
        self.logger = logger

        self.ice_shield = False
        self.attacks_received = 0
        self.bonus_armor = 0

    def equip_ice_shield(self):
        self.ice_shield = True
        print(self.name, "equipped Ice Shield")

    def take_damage(self, damage, damage_type):
        self.attacks_received += 1

        if self.ice_shield and damage_type == "fire":
            if self.attacks_received % 3 == 0:
                self.bonus_armor += damage

                print("Ice Shield absorbed the damage")
                print("Bonus armor:", self.bonus_armor)

                return 0

        final_damage = max(0, damage - self.bonus_armor)

        self.health -= final_damage

        return final_damage

    def attack_target(self, target):
        damage = self.attack

        if self.name == "Necromancer" and self.is_low_health():
            damage = self.attack * 0.5

            final_damage = target.take_damage(
                damage,
                "fire"
            )

            if self.logger:
                self.logger.log_attack(
                    self.name,
                    target.name,
                    "elemental",
                    final_damage
                )

            heal = self.attack * 0.5

            self.health = min(
                self.max_health,
                self.health + heal
            )

            print("Necromancer Life-Drain")
            print("Healed:", heal)
            print("Attacker:", self.name)
            print("Damage Type: elemental")
            print("Raw Damage:", damage)
            print("Target Mitigation:", damage - final_damage)
            print("Final HP remaining:", target.health)
            print()

            return

        if self.name == "Rogue" and random.random() < 0.25:
            damage = damage * 2
            print("Critical hit")

        final_damage = target.take_damage(
            damage,
            self.damage_type
        )

        if self.logger:
            self.logger.log_attack(
                self.name,
                target.name,
                self.damage_type,
                final_damage
            )

        print("Attacker:", self.name)
        print("Damage Type:", self.damage_type)
        print("Raw Damage:", damage)
        print("Target Mitigation:", damage - final_damage)
        print("Final HP remaining:", target.health)
        print()


class Warrior(Character):
    def __init__(self, logger=None):
        super().__init__(
            "Warrior",
            120,
            30,
            "physical",
            logger
        )


class Mage(Character):
    def __init__(self, logger=None):
        super().__init__(
            "Mage",
            80,
            40,
            "fire",
            logger
        )


class Rogue(Character):
    def __init__(self, logger=None):
        super().__init__(
            "Rogue",
            100,
            25,
            "physical",
            logger
        )


class Necromancer(Character):
    def __init__(self, logger=None):
        super().__init__(
            "Necromancer",
            100,
            30,
            "physical",
            logger
        )

    def is_low_health(self):
        return self.health < self.max_health * 0.25
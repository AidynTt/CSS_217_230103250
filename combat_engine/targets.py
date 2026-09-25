class Target:
    def __init__(self, name, health, physical_defense, fire_defense):
        self.name = name
        self.health = health
        self.physical_defense = physical_defense
        self.fire_defense = fire_defense

    def take_damage(self, damage, damage_type):
        if damage_type == "physical":
            defense = self.physical_defense
        else:
            defense = self.fire_defense

        final_damage = max(0, damage - defense)

        self.health -= final_damage

        return final_damage


class ArmoredDummy(Target):
    def __init__(self):
        super().__init__(
            "Armored Dummy",
            100,
            10,
            10
        )


class EtherealWisp(Target):
    def __init__(self):
        super().__init__(
            "Ethereal Wisp",
            100,
            35,
            0
        )
from characters import Warrior, Mage, Rogue, Necromancer
from targets import ArmoredDummy, EtherealWisp
from logging_system import CombatLogger


logger = CombatLogger()


characters = [
    Warrior(logger),
    Mage(logger),
    Rogue(logger),
    Necromancer(logger)
]


for character in characters:
    dummy = ArmoredDummy()
    wisp = EtherealWisp()

    character.attack_target(dummy)
    character.attack_target(wisp)


print("Necromancer LifeDrain test")

necromancer = Necromancer(logger)
necromancer.health = 20

dummy = ArmoredDummy()

necromancer.attack_target(dummy)


print("Ice Shield Test")

mage = Mage(logger)
mage.equip_ice_shield()

fire_mage = Mage(logger)


print("Fire attack 1")
fire_mage.attack_target(mage)

print("Fire attack 2")
fire_mage.attack_target(mage)

print("Fire attack 3")
fire_mage.attack_target(mage)


print("CSV LOG")
print(logger.get_csv())
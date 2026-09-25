class LegacyLogger:
    def write_log(self, level, app_name, message):
        print(f"[{app_name}] Level {level}: {message}")


class ConsoleLoggerAdapter:
    def __init__(self, logger, app_name):
        self.logger = logger
        self.app_name = app_name

    def info(self, message):
        self.logger.write_log(1, self.app_name, message)

    def warn(self, message):
        self.logger.write_log(2, self.app_name, message)

    def error(self, message):
        self.logger.write_log(3, self.app_name, message)


legacy = LegacyLogger()
logger = ConsoleLoggerAdapter(legacy, "MyApp")

logger.info("Application started")
logger.warn("Low memory")
logger.error("Something went wrong")
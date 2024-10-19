module.exports = {
    preset: 'jest-preset-angular',
    setupFilesAfterEnv: ['<rootDir>/src/setup-jest.ts'],
    testEnvironment: 'jsdom',
    transformIgnorePatterns: ['node_modules/(?!.*\\.mjs$)'],
  };
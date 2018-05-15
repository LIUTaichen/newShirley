import { NiggleQuattraModule } from './niggle-quattra.module';

describe('NiggleQuattraModule', () => {
  let niggleQuattraModule: NiggleQuattraModule;

  beforeEach(() => {
    niggleQuattraModule = new NiggleQuattraModule();
  });

  it('should create an instance', () => {
    expect(niggleQuattraModule).toBeTruthy();
  });
});

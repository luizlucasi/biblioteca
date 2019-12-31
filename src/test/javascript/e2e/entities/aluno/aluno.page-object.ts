import { element, by, ElementFinder } from 'protractor';

export class AlunoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-aluno div table .btn-danger'));
  title = element.all(by.css('jhi-aluno div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class AlunoUpdatePage {
  pageTitle = element(by.id('jhi-aluno-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nomeInput = element(by.id('field_nome'));
  turmaInput = element(by.id('field_turma'));
  matriculaInput = element(by.id('field_matricula'));
  celularInput = element(by.id('field_celular'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setTurmaInput(turma: string): Promise<void> {
    await this.turmaInput.sendKeys(turma);
  }

  async getTurmaInput(): Promise<string> {
    return await this.turmaInput.getAttribute('value');
  }

  async setMatriculaInput(matricula: string): Promise<void> {
    await this.matriculaInput.sendKeys(matricula);
  }

  async getMatriculaInput(): Promise<string> {
    return await this.matriculaInput.getAttribute('value');
  }

  async setCelularInput(celular: string): Promise<void> {
    await this.celularInput.sendKeys(celular);
  }

  async getCelularInput(): Promise<string> {
    return await this.celularInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class AlunoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-aluno-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-aluno'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}

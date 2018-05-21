import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { MatTableDataSource, MatSort, MatDialog } from '@angular/material';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { Niggle, Priority } from '../../../entities/niggle/niggle.model';
import { Plant } from '../../../entities/Plant/plant.model';
import { NiggleRow } from './niggle-row.model';
import { NiggleService } from '../../../entities/niggle/niggle.service';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from '../../../shared';
import { NiggleCreateDialogComponent } from './niggle-create-dialog/niggle-create-dialog.component';
import { NiggleEditDialogComponent } from './niggle-edit-dialog/niggle-edit-dialog.component';
import { NiggleDeleteDialogDwComponent } from './niggle-delete-dialog-dw/niggle-delete-dialog-dw.component';

@Component({
  selector: 'jhi-niggle-list-dw',
  templateUrl: './niggle-list-dw.component.html',
  styleUrls: ['./niggle-list-dw.component.css']
})
export class NiggleListDwComponent implements OnInit, OnDestroy {

  niggles: Niggle[];
  idOfFocusedRow;
  displayedColumns = [
    'plantNumber',
    'auditNo',
    'quattraReference',
    'plantDescription',
    'site',
    'locationUpdateTime',
    'description',
    'status',
    'contractor',
    'quattraComments',
    'owner',
    'dateOpened',
    'daysOpened',
    'priorityOrder',
    'createdBy',
    'createdDate',
    'lastModifiedBy',
    'lastModifiedDate',
    'delete'
  ];
  dataSource: MatTableDataSource<NiggleRow>;
  @ViewChild(MatSort) sort: MatSort;

  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private niggleService: NiggleService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal,
    public dialog: MatDialog,
  ) {
  }

  loadAll() {
    this.niggleService.query().subscribe(
      (res: HttpResponse<Niggle[]>) => {
        this.niggles = res.body;
        const rows = this.niggles.map(this.convertEntityToRow, this);
        this.dataSource = new MatTableDataSource(rows);
        this.dataSource.sort = this.sort;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  ngOnInit() {
    this.loadAll();
    this.principal.identity().then((account) => {
      this.currentAccount = account;
    });
    this.registerChangeInNiggles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: Niggle) {
    return item.id;
  }
  registerChangeInNiggles() {
    this.eventSubscriber = this.eventManager.subscribe('niggleListModification', (response) => this.loadAll());
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  getDaysOpened(niggle: Niggle) {
    if (niggle.dateOpened) {
      return Math.floor(Math.abs(niggle.dateOpened.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
    } else {
      return null;
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(NiggleCreateDialogComponent, {
      width: '500px',
      panelClass: 'niggle-panel',
      data: { name: 'this.name', animal: 'this.animal' }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.idOfFocusedRow = '';
      // this.animal = result;
    });
  }

  openDeleteDialog(id: number): void {
    this.idOfFocusedRow = id;
    const dialogRef = this.dialog.open(NiggleDeleteDialogDwComponent, {
      panelClass: 'niggle-delete-panel',
      data: { id }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.idOfFocusedRow = '';
    });
  }

  openEditDialog(id: number): void {
    this.idOfFocusedRow = id;
    const niggle: Niggle = this.niggles.find((niggleElement) => niggleElement.id === id);
    const dialogRef = this.dialog.open(NiggleEditDialogComponent, {
      width: '500px',
      panelClass: 'niggle-panel',
      data: { niggle }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }

  convertEntityToRow(niggle: Niggle): NiggleRow {
    const niggleDaysOpened = this.getDaysOpened(niggle);
    let fleetId, plantDesctiption, siteAndName, location, locationUpdateTime, owner, contractor, auditNo;
    if (niggle.plant) {
      const plant: Plant = niggle.plant;
      fleetId = plant.fleetId;
      plantDesctiption = plant.description;
      siteAndName = plant.project ? plant.project['jobNumber'] + ' ' + plant.project['name'] : '';
      location = plant.location;
      locationUpdateTime = plant.lastLocationUpdateTime;
      owner = plant.owner ? plant.owner['company'] : '';
    }
    contractor = niggle.assignedContractor ? niggle.assignedContractor['name'] : '';
    auditNo = niggle.purchaseOrder ? niggle.purchaseOrder.id : '';
    const priorityOrder: any = Priority[niggle.priority];
    const niggleRow: NiggleRow = {
      id: niggle.id,
      description: niggle.description,
      auditNo,
      status: niggle.status,
      note: niggle.note,
      priority: niggle.priority,
      priorityOrder,
      quattraReference: niggle.quattraReference,
      quattraComments: niggle.quattraComments,
      dateOpened: niggle.dateOpened,
      dateUpdated: niggle.lastModifiedDate,
      dateClosed: niggle.dateClosed,
      plantNumber: fleetId,
      plantDescription: plantDesctiption,
      site: siteAndName,
      location,
      locationUpdateTime,
      owner,
      contractor,
      daysOpened: niggleDaysOpened,
      createdBy: niggle.createdBy,
      createdDate: niggle.createdDate,
      lastModifiedBy: niggle.lastModifiedBy,
      lastModifiedDate: niggle.lastModifiedDate
    };
    return niggleRow;
  }

}

import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { MatTableDataSource, MatSort, MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { Niggle } from '../niggle.model';
import { NiggleService } from '../niggle.service';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from '../../../shared';
import { Plant } from '../../Plant/plant.model';
import { NiggleRow } from '../niggle-list-dw/niggle-row.model';
import { CreateDialogQuattraComponent } from './create-dialog-quattra/create-dialog-quattra.component';
import { EditDialogQuattraComponent } from './edit-dialog-quattra/edit-dialog-quattra.component';
import { DeleteDialogQuattraComponent } from './delete-dialog-quattra/delete-dialog-quattra.component';

@Component({
  selector: 'jhi-niggle-list-quattra',
  templateUrl: './niggle-list-quattra.component.html',
  styleUrls: ['./niggle-list-quattra.component.css']
})
export class NiggleListQuattraComponent implements OnInit, OnDestroy {

  niggles: Niggle[];
  idOfFocusedRow;
  displayedColumns = ['priority', 'plantNumber', 'quattraReference', 'plantDescription', 'location', 'locationUpdateTime',
    'description',
    'status',
    'quattraComments',
    'dateOpened',
    'daysOpened',
    'delete'];
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
  ) { }

  loadAll() {
    this.niggleService.query().subscribe(
      (res: HttpResponse<Niggle[]>) => {
        this.niggles = res.body;
        const rows = this.niggles.filter((niggle) => {
          if ( niggle.assignedContractor['name'] === 'Quattra') {
            return true;
          }else {
            return false;
          }
        }).map(this.convertEntityToRow, this);
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

  convertEntityToRow(niggle: Niggle): NiggleRow {
    const niggleDaysOpened = this.getDaysOpened(niggle);
    let fleetId, plantDesctiption, siteAndName, location, locationUpdateTime, owner, contractor;
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
    const niggleRow: NiggleRow = {
      id: niggle.id,
      description: niggle.description,
      status: niggle.status,
      note: niggle.note,
      priority: niggle.priority,
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

  openDialog(): void {
    const dialogRef = this.dialog.open(CreateDialogQuattraComponent, {
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
    const dialogRef = this.dialog.open(DeleteDialogQuattraComponent, {
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
    const dialogRef = this.dialog.open(EditDialogQuattraComponent, {
      width: '500px',
      panelClass: 'niggle-panel',
      data: { niggle }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }

}
